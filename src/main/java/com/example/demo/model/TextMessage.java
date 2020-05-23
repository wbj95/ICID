package com.example.demo.model;

public class TextMessage extends BaseMessage {
    private String Content;//用户公众号传过来的内容

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
