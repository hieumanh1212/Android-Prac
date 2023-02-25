package com.example.myapplication;

public class Contact {
    private int id;
    private String images;
    private String name;
    private String phone;
    private boolean check;
    public Contact(){

    }

    public Contact(int id, String images, String name, String phone, boolean check) {
        this.id = id;
        this.images = images;
        this.name = name;
        this.phone = phone;
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
