package com.example.fly;

public class Users {

    String uid;
    String num;
    String email;
    String name;
    String pass;
    String imageuri;

    public Users() {
    }

    public Users(String uid, String num, String email, String name, String pass, String imageuri) {
        this.uid = uid;
        this.num = num;
        this.email = email;
        this.name = name;
        this.pass = pass;
        this.imageuri = imageuri;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}
