package com.example.demo.service;

import com.example.demo.dao.BookRegisterDao;
import com.example.demo.dao.StaffDao;
import com.example.demo.model.BookRegister;
import com.example.demo.model.Staff;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class BookRegistServer {


    @Autowired
   BookRegisterDao bookRegisterDao;
    @Autowired
  StaffServer staffServer;

    //查询所有书籍信息
    public JSONObject queryAllBook(){
        JSONObject jsonObject=new JSONObject();

        List<BookRegister> res=bookRegisterDao.querayAllBook();
    //    List<Staff> s=  staffDao.queryStaffByName("韦保俊");
    //    System.out.println("结果："+s.get(0).getStaffName());
        JSONArray jsonArray = JSONArray.fromObject(res);

        jsonObject.put("bookdata",jsonArray);

        System.out.println(jsonObject.toString());
        return jsonObject;
    }

    //借书
    public JSONObject borrowBook(String borrowerName,String borrowerIphone,String BookNO){
        JSONObject jsonObject=new JSONObject();
        System.out.println(borrowerName);
        //根据姓名查询是否是本部门人员
   // List<Staff> s=  staffDao.queryStaffByName(borrowerName);
        boolean s=staffServer.isStaff(borrowerName);
        System.out.println(s);
        if (s){
            //查有此人
            //获取当前日期

            SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy-MM-dd ");
            String nextMon=nextMonth();

            String currentDate =   dateFormat.format( new Date() );
            //更新表
          int res=  bookRegisterDao.updateBook(BookNO,"借出",borrowerName,borrowerIphone,currentDate,nextMon);
          System.out.println(res);
            jsonObject.put("data","1");//借书成功
        }else {
            jsonObject.put("data","0");//借书失败
        }
        System.out.println(jsonObject);
        return jsonObject;
    }

    //还书
    public JSONObject returnBookServer(String name,String bookNo){
        JSONObject jsonObject=new JSONObject();
        //先检查人名是否是借书人
        BookRegister bookRegister=bookRegisterDao.queryBorrowerName(bookNo);
        if (bookRegister.getBorrowerName().equals(name)){
            //查有此人
            //还书,更新表
            bookRegisterDao.returnbook(bookNo,"在库");
            jsonObject.put("data","1");//还书成功
        }else {
            jsonObject.put("data","0");//还书失败
        }
        return jsonObject;
    }

    //续借
    public JSONObject renewBook(String name,String bookNo){
        JSONObject jsonObject = new JSONObject();

        //先查是否是续借人
        BookRegister bookRegister=bookRegisterDao.queryBorrowerName(bookNo);
        if(bookRegister.getBorrowerName().equals(name)){
            SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy-MM-dd ");
            String nextMon=nextMonth();

            String currentDate =   dateFormat.format( new Date() );
            //查有此人
            //更新续借信息
            if (bookRegister.getRenew()==null){

                bookRegisterDao.renewBook("1",bookNo,currentDate,nextMon);
                jsonObject.put("data","1");//续借成功
            }
           else if (bookRegister.getRenew().equals("1")){
                //已经续借过一次，不能再续借了
                jsonObject.put("data","3");//续借次数超过了
            }else {
                bookRegisterDao.renewBook("1",bookNo,currentDate,nextMon);
                jsonObject.put("data","1");//续借成功
            }
        }else {
            jsonObject.put("data","0");//续借失败，人名不对
        }
        return jsonObject;
    }

   //获取下一个的日期
    public String nextMonth(){
        Date date = new Date();
        int year=Integer.parseInt(new SimpleDateFormat("yyyy").format(date));//取到年份值
        int month=Integer.parseInt(new SimpleDateFormat("MM").format(date))+1;//取到鱼粉值
        int day=Integer.parseInt(new SimpleDateFormat("dd").format(date));//取到天值
        if(month==0){
            year-=1;month=12;
        }
        else if(day>28){
            if(month==2){
                if(year%400==0||(year %4==0&&year%100!=0)){
                    day=29;
                }else day=28;
            }else if((month==4||month==6||month==9||month==11)&&day==31)
            {
                day=30;
            }
        }
        String y = year+"";String m ="";String d ="";
        if(month<10) m = "0"+month;
        else m=month+"";
        if(day<10) d = "0"+day;
        else d = day+"";
        System.out.println(y+"-"+m+"-"+d);
        return y+"-"+m+"-"+d;
    }
}
