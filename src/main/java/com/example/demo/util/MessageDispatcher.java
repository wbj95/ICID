package com.example.demo.util;

import com.example.demo.model.TextMessage;
import com.example.demo.model.carInfo;
import com.example.demo.service.CarInfoServer;
import org.springframework.stereotype.Service;

import java.time.format.SignStyle;
import java.util.Date;
import java.util.Map;
public class MessageDispatcher {
    private static CarInfoServer carInfoServer;
    public static String processMessage(Map<String, String> map,String borrow,String iphone) {
        String openid = map.get("FromUserName"); //用户 openid
        String mpid = map.get("ToUserName");   //公众号原始 ID
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
            //普通文本消息
            TextMessage txtmsg = new TextMessage();
            txtmsg.setToUserName(openid);
            txtmsg.setFromUserName(mpid);
            txtmsg.setCreateTime(new Date().getTime());
            txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            if (borrow.equals("")&iphone.equals("")){//查询出来的信息为空
                txtmsg.setContent("输入vin号不正确，请重新输入");
            }else {
                txtmsg.setContent("车辆负责人："+borrow+"  电话号码："+iphone);//正确返回
            }
            return MessageUtil.textMessageToXml(txtmsg);
        }
        return null;
    }
    public String processEvent(Map<String, String> map) {
        String openid = map.get("FromUserName"); //用户 openid
        String mpid = map.get("ToUserName");   //公众号原始 ID

        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {//用户订阅的回复

            //普通文本消息
            TextMessage txtmsg = new TextMessage();
            txtmsg.setToUserName(openid);
            txtmsg.setFromUserName(mpid);
            txtmsg.setCreateTime(new Date().getTime());
            txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            txtmsg.setContent("感谢关注，查询车辆信息人请直接输入VIN号,例如：LK6ADCE29KB150869");
            return MessageUtil.textMessageToXml(txtmsg);
        }
        return null;
    }
}
