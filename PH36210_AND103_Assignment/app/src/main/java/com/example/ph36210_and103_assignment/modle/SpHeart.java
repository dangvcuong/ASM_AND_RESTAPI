package com.example.ph36210_and103_assignment.modle;

import com.google.gson.annotations.SerializedName;

public class SpHeart {
    @SerializedName("_id")
    private String id;
    private String shose_Name_heart, description_heart, image_anh_heart, id_Shose_heart,id_User_heart, createdAt, updatedAt;
    private int price_heart;

    public SpHeart() {
    }

    public SpHeart(String id, String shose_Name_heart, String description_heart, String image_anh_heart, String id_Shose_heart, String id_User_heart, String createdAt, String updatedAt, int price_heart) {
        this.id = id;
        this.shose_Name_heart = shose_Name_heart;
        this.description_heart = description_heart;
        this.image_anh_heart = image_anh_heart;
        this.id_Shose_heart = id_Shose_heart;
        this.id_User_heart = id_User_heart;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.price_heart = price_heart;
    }

    public String getId_User_heart() {
        return id_User_heart;
    }

    public void setId_User_heart(String id_User_heart) {
        this.id_User_heart = id_User_heart;
    }

    public String getId_Shose_heart() {
        return id_Shose_heart;
    }

    public void setId_Shose_heart(String id_Shose_heart) {
        this.id_Shose_heart = id_Shose_heart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShose_Name_heart() {
        return shose_Name_heart;
    }

    public void setShose_Name_heart(String shose_Name_heart) {
        this.shose_Name_heart = shose_Name_heart;
    }


    public String getDescription_heart() {
        return description_heart;
    }

    public void setDescription_heart(String description_heart) {
        this.description_heart = description_heart;
    }

    public String getImage_anh_heart() {
        return image_anh_heart;
    }

    public void setImage_anh_heart(String image_anh_heart) {
        this.image_anh_heart = image_anh_heart;
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

    public int getPrice_heart() {
        return price_heart;
    }

    public void setPrice_heart(int price_heart) {
        this.price_heart = price_heart;
    }
}
