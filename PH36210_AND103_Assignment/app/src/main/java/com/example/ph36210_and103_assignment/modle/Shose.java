package com.example.ph36210_and103_assignment.modle;

import com.google.gson.annotations.SerializedName;

public class Shose {
    @SerializedName("_id")
    private String id;
    private String shoseName, description, image_anh, createdAt, updatedAt;
    private int price;

    public Shose() {
    }

    public Shose(String id, String shoseName, String description, String image_anh, String createdAt, String updatedAt,  int price) {
        this.id = id;
        this.shoseName = shoseName;
        this.description = description;
        this.image_anh = image_anh;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShoseName() {
        return shoseName;
    }

    public void setShoseName(String shoseName) {
        this.shoseName = shoseName;
    }

    public String getImage_anh() {
        return image_anh;
    }

    public void setImage_anh(String image_anh) {
        this.image_anh = image_anh;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
