package com.example.ph36210_and103_assignment.modle;

import java.io.Serializable;

public class users implements Serializable {
        private String _id, email, password, fullname, hinh_anh_user, ngay_sinh_user, sdt_user,dia_chi_user;
    private String createAt, updatedAt;

    public users() {
    }

    public users(String _id, String email, String password, String fullname, String hinh_anh_user, String ngay_sinh_user, String sdt_user, String dia_chi_user, String createAt, String updatedAt) {
        this._id = _id;
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.hinh_anh_user = hinh_anh_user;
        this.ngay_sinh_user = ngay_sinh_user;
        this.sdt_user = sdt_user;
        this.dia_chi_user = dia_chi_user;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
    }

    public String getDia_chi_user() {
        return dia_chi_user;
    }

    public void setDia_chi_user(String dia_chi_user) {
        this.dia_chi_user = dia_chi_user;
    }

    public String getHinh_anh_user() {
        return hinh_anh_user;
    }

    public void setHinh_anh_user(String hinh_anh_user) {
        this.hinh_anh_user = hinh_anh_user;
    }

    public String getNgay_sinh_user() {
        return ngay_sinh_user;
    }

    public void setNgay_sinh_user(String ngay_sinh_user) {
        this.ngay_sinh_user = ngay_sinh_user;
    }

    public String getSdt_user() {
        return sdt_user;
    }

    public void setSdt_user(String sdt_user) {
        this.sdt_user = sdt_user;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
