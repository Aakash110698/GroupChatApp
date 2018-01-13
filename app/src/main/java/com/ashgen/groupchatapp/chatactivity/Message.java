package com.ashgen.groupchatapp.chatactivity;

/**
 * Created by malavan on 13/01/18.
 */

public class Message {
    String name;
    String text;
    String color;
    String time;
    String type;
    String uniqueid;
    String key;


    public Message(String name, String text, String color, String time, String type, String uniqueid, String key) {
        this.name = name;
        this.text = text;
        this.color = color;
        this.time = time;
        this.type = type;
        this.uniqueid = uniqueid;
        this.key = key;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Message() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }
}
