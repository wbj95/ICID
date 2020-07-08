package com.example.demo.controller;

import com.example.demo.service.BookRegistServer;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RequestMapping(path = "/BookRegist")
@RestController
public class BookRegistController {

    @Autowired
    BookRegistServer bookRegistServer;

    //查询所有书籍信息
    @RequestMapping(path = {"/getAllBook"},method = {RequestMethod.GET})
    public JSONObject ListAllBook(){

        JSONObject j=bookRegistServer.queryAllBook();
        return j;

    }
    //借书
    @RequestMapping(path = {"/getOneBook"},method = {RequestMethod.GET})
    public JSONObject borrowBooks(@RequestParam(value = "borrowerName", required = true) String borrowerName,
                                  @RequestParam(value = "borrowerIphone", required = true) String borrowerIphone,
                                  @RequestParam(value = "bookID", required = true) String bookID){
        JSONObject j=bookRegistServer.borrowBook(borrowerName,borrowerIphone,bookID);
        return j;
    }

    //还书
    //借书
    @RequestMapping(path = {"/returnbook"},method = {RequestMethod.GET})
    public JSONObject returnBook(@RequestParam(value = "borrowerName", required = true) String borrowerName,
                                  @RequestParam(value = "bookID", required = true) String bookID){
        JSONObject jsonObject=bookRegistServer.returnBookServer(borrowerName,bookID);
        return jsonObject;
    }
    //续借
    @RequestMapping(path = {"/renewBook"},method = {RequestMethod.GET})
    public JSONObject renewBook(@RequestParam(value = "borrowerName", required = true) String borrowerName,
                                 @RequestParam(value = "bookID", required = true) String bookID){
       // JSONObject jsonObject=bookRegistServer.returnBookServer(borrowerName,bookID);
        JSONObject j=bookRegistServer.renewBook(borrowerName,bookID);
        return j;
    }
}
