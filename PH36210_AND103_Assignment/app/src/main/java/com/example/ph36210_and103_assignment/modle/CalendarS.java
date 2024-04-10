package com.example.ph36210_and103_assignment.modle;

import com.google.gson.annotations.SerializedName;

public class CalendarS {
    @SerializedName("_id")
    private String id;
    private String id_Shose_Calendar, id_cart_Calendar,date_Calendar,ten_shose_Calendar,hinh_anh_Calendar,id_User_Calendar;
    private int gia_shose_Calendar;

    public CalendarS() {
    }

    public CalendarS(String id, String id_Shose_Calendar, String id_cart_Calendar, String date_Calendar, String ten_shose_Calendar, String hinh_anh_Calendar, String id_User_Calendar, int gia_shose_Calendar) {
        this.id = id;
        this.id_Shose_Calendar = id_Shose_Calendar;
        this.id_cart_Calendar = id_cart_Calendar;
        this.date_Calendar = date_Calendar;
        this.ten_shose_Calendar = ten_shose_Calendar;
        this.hinh_anh_Calendar = hinh_anh_Calendar;
        this.id_User_Calendar = id_User_Calendar;
        this.gia_shose_Calendar = gia_shose_Calendar;
    }

    public String getId_User_Calendar() {
        return id_User_Calendar;
    }

    public void setId_User_Calendar(String id_User_Calendar) {
        this.id_User_Calendar = id_User_Calendar;
    }

    public String getTen_shose_Calendar() {
        return ten_shose_Calendar;
    }

    public void setTen_shose_Calendar(String ten_shose_Calendar) {
        this.ten_shose_Calendar = ten_shose_Calendar;
    }

    public String getHinh_anh_Calendar() {
        return hinh_anh_Calendar;
    }

    public void setHinh_anh_Calendar(String hinh_anh_Calendar) {
        this.hinh_anh_Calendar = hinh_anh_Calendar;
    }

    public int getGia_shose_Calendar() {
        return gia_shose_Calendar;
    }

    public void setGia_shose_Calendar(int gia_shose_Calendar) {
        this.gia_shose_Calendar = gia_shose_Calendar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_Shose_Calendar() {
        return id_Shose_Calendar;
    }

    public void setId_Shose_Calendar(String id_Shose_Calendar) {
        this.id_Shose_Calendar = id_Shose_Calendar;
    }

    public String getId_cart_Calendar() {
        return id_cart_Calendar;
    }

    public void setId_cart_Calendar(String id_cart_Calendar) {
        this.id_cart_Calendar = id_cart_Calendar;
    }

    public String getDate_Calendar() {
        return date_Calendar;
    }

    public void setDate_Calendar(String date_Calendar) {
        this.date_Calendar = date_Calendar;
    }
}
