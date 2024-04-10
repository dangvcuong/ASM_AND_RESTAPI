package com.example.ph36210_and103_assignment.modle;

import com.google.gson.annotations.SerializedName;

public class Cart {
    @SerializedName("_id")
    private String id;

    private String ten_shose_cart,hinh_anh_cart,id_Shose_cart,id_User_cart;
    private int gia_shose_cart;

    public Cart() {
    }

    public Cart(String id, String ten_shose_cart, String hinh_anh_cart, String id_Shose_cart, String id_User_cart, int gia_shose_cart) {
        this.id = id;
        this.ten_shose_cart = ten_shose_cart;
        this.hinh_anh_cart = hinh_anh_cart;
        this.id_Shose_cart = id_Shose_cart;
        this.id_User_cart = id_User_cart;
        this.gia_shose_cart = gia_shose_cart;
    }

    public String getId_User_cart() {
        return id_User_cart;
    }

    public void setId_User_cart(String id_User_cart) {
        this.id_User_cart = id_User_cart;
    }

    public String getId_Shose_cart() {
        return id_Shose_cart;
    }

    public void setId_Shose_cart(String id_Shose_cart) {
        this.id_Shose_cart = id_Shose_cart;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen_shose_cart() {
        return ten_shose_cart;
    }

    public void setTen_shose_cart(String ten_shose_cart) {
        this.ten_shose_cart = ten_shose_cart;
    }

    public String getHinh_anh_cart() {
        return hinh_anh_cart;
    }

    public void setHinh_anh_cart(String hinh_anh_cart) {
        this.hinh_anh_cart = hinh_anh_cart;
    }

    public int getGia_shose_cart() {
        return gia_shose_cart;
    }

    public void setGia_shose_cart(int gia_shose_cart) {
        this.gia_shose_cart = gia_shose_cart;
    }
}
