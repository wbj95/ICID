package com.example.demo.controller;

import com.example.demo.dao.CarInfoDao;
import com.example.demo.model.carInfo;
import com.example.demo.service.CarInfoServer;
import com.example.demo.util.AliyunOSSUtil;
import com.example.demo.util.MessageDispatcher;
import com.example.demo.util.MessageUtil;
import com.example.demo.util.SignUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Arrays;
import java.util.Map;


@RequestMapping(path = "/CarInfo")
@RestController
public class CarInfoController {

    private CarInfoServer carInfoServer;
    private CarInfoDao carInfoDao;
    private SignUtil signUtil;
    AliyunOSSUtil aliyun=new AliyunOSSUtil();

    public CarInfoController(CarInfoServer carInfoServer) {
        this.carInfoServer = carInfoServer;
    }
  //查询所有车辆信息
    @RequestMapping(path = {"/getAllCarInfo"},method = {RequestMethod.GET})
    public JSONObject ListCar(){
        return carInfoServer.queryAllCarInfo();
    }

    //查询版本，为了躲避腾讯审核
    @RequestMapping(path = {"/findversion"},method = {RequestMethod.GET})
    public String findVersion(){
      String j="0";
        return j;
    }
    //删除车辆
    @RequestMapping(path = {"/DeleteCar"},method = {RequestMethod.GET})
    public int DeleteCarByID(@RequestParam(value = "id", required = true) int id){
        System.out.println("删除ID"+id);
       return carInfoServer.DeleteCar(id);
    }
    //新增车辆
    @RequestMapping(path = {"/AddCar"},method = {RequestMethod.GET})
    public int AddCar(@RequestParam(value = "projectName", required = true) String projectName,
                      @RequestParam(value = "vin", required = true) String vin,
                      @RequestParam(value = "borrower", required = true) String borrower,
                      @RequestParam(value = "configuration", required = true) String configuration,
                      @RequestParam(value = "state", required = true) String state
                      ){
        System.out.println(projectName+vin+borrower+"测试");
        carInfo c = new carInfo();
       c.setProjectName(projectName);
        c.setVin(vin);
        c.setBorrower(borrower);
        c.setConfiguration(configuration);
        c.setState(state);
       return carInfoServer.AddCar(c);
    }
    //修改车辆信息
    @RequestMapping(path = {"/updateCar"},method = {RequestMethod.GET})
    public int updateCar(@RequestParam(value = "projectName", required = true) String projectName,
                         @RequestParam(value = "vin", required = true) String vin,
                         @RequestParam(value = "borrower", required = true) String borrower,
                         @RequestParam(value = "id", required = true) int id,
                         @RequestParam(value = "configuration", required = true) String configuration,
                         @RequestParam(value = "state", required = true) String state
                         ){
        System.out.println("参数"+configuration+state);
        return carInfoServer.updateCar(id,projectName,vin,borrower,configuration,state);

    }
    //查询剩余电量,定位，本周里程，本周启动次数
    @RequestMapping(path = {"/getCarInfoByVin"},method = {RequestMethod.GET})
    public JSONObject QueryCarByInfoID(@RequestParam(value = "vin", required = true) String Vin){
        return carInfoServer.queryCarInfoInfoByVin(Vin);
    }
    //查询排行
    @RequestMapping(path = {"/getCarRanking"},method = {RequestMethod.GET})
    public JSONObject QueryCarRanking(){
        return carInfoServer.queryRankin();
    }

    //借车
    @RequestMapping(path = {"/exchangeNameAndIphone"},method = {RequestMethod.GET})
    public int AddCar(@RequestParam(value = "vin", required = true) String vin,
                      @RequestParam(value = "Name", required = true) String Name,
                      @RequestParam(value = "phoneNum", required = true) String phoneNum
    ){
        System.out.println(vin+Name+phoneNum);
       return carInfoServer.updateBorrow(vin,Name,phoneNum);
    }

    //通过vin号模糊查询
    @RequestMapping(path = {"/fuzzyQuery"},method = {RequestMethod.GET})
    public JSONObject fuzzyQuery(@RequestParam(value = "vin", required = true) String vin
    ){
        System.out.println(vin);
        return carInfoServer.fuzzyQuery(vin);
    }
    //通过vin号查询最近七天行程
    @RequestMapping(path = {"/QueryTrip"},method = {RequestMethod.GET})
    public JSONObject QueryTrip(@RequestParam(value = "vin", required = true) String vin
    ){
        System.out.println(vin);
        return carInfoServer.findTrip(vin);
    }
    //小程序传关键词数组来查询文件
    @RequestMapping(path = {"/findFiles"},method = {RequestMethod.POST})
    public JSONObject findFiles(@RequestParam("array")String[] array,@RequestParam("cusid")String cusid)
    {
        System.out.println(Arrays.toString(array));//打印出数组
        JSONObject jsonObject=  carInfoServer.findFile(array);
        return jsonObject;
    }
   //小程序传过来是数字查询文件
   @RequestMapping(path = {"/findFilesByNum"},method = {RequestMethod.GET})
   public JSONObject findFilesByNum(@RequestParam(value = "fileName", required = true) String fileName)
   {
       System.out.println("1111"+fileName);

      JSONObject j=carInfoServer.findUrl(fileName);
      return j;
   }
    //知识库传过来查URL
    @RequestMapping(path = {"/findFilesURL"},method = {RequestMethod.GET})
    public JSONObject findFilesURL(@RequestParam(value = "fileName", required = true) String fileName)
    {
        System.out.println("1111"+fileName);

        URL url=aliyun.getUrlBy(fileName);
        JSONObject j=new JSONObject();
        //生成的url是http，不是https
        String u=url.toString();
        j.put("url",u.replace("http","https"));
        return j;
    }
    //小程序传过来是数字查询文件
    @RequestMapping(path = {"/listFile"},method = {RequestMethod.GET})
    public JSONObject listFile()
    {

        JSONObject j=carInfoServer.getDir();
        return j;
    }

    //获取video的所有URL和videoname
    @RequestMapping(path = {"/listvideo"},method = {RequestMethod.GET})
    public JSONObject listvideo()
    {
        JSONObject j=carInfoServer.getvideoList();
        return j;
    }

    //获取微信公众号发过来的东西，这个是验证签名
    @RequestMapping(path = {"/getWeChatInfo"},method = {RequestMethod.GET})
    public void  getWeChatInfo(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
       System.out.println("梁仁德");
        request.setCharacterEncoding("UTF-8");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (signUtil.checkSignature(signature, timestamp, nonce)) {
                System.out.println("-------验证微信服务器结束--------");
                out.write(echostr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }
   //公众号发过来的POST请求。
    @RequestMapping(path = {"/getWeChatInfo"},method = {RequestMethod.POST})
    public String WechantHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        Map<String, String> map = MessageUtil.parseXml(request);//将传过来的数据解析成map类型

        String msgType = map.get("MsgType");//获取文本类型
        MessageDispatcher messageDispatcher = new MessageDispatcher();
        if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {//如果是event类型
            return messageDispatcher.processEvent(map);
        }else{
            String content=map.get("Content");
            carInfo c=carInfoServer.queryOneCarInfo(content);
            System.out.println(c);
            if (c==null){//通过vin号查询出来的结果为空，borrow和iphone都为空
                return messageDispatcher.processMessage(map,"","");
            }else {//查询出了结果，将数值传入方法中
                String borrow=c.getBorrower();
                String iphone=c.getIphoneNum();
                return messageDispatcher.processMessage(map,borrow,iphone);
            }
        }
    }
}
