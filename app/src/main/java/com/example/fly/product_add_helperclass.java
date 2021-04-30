package com.example.fly;

public class product_add_helperclass {

    private String uid, detail, description,category,model,price ,image;


    public product_add_helperclass() {
    }


    public product_add_helperclass(String uid, String detail, String description, String category ,String model ,String price,String image) {

        this.uid = uid;
        this.detail = detail;
        this.description = description;
        this.image = image;
        this.model = model;
        this.category = category;
        this.price = price;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
