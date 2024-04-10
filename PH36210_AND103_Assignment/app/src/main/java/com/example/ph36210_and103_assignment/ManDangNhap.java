package com.example.ph36210_and103_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ph36210_and103_assignment.modle.Response;
import com.example.ph36210_and103_assignment.modle.users;
import com.example.ph36210_and103_assignment.servers.HttpActivity;

import retrofit2.Call;
import retrofit2.Callback;

public class ManDangNhap extends AppCompatActivity {
    TextView btn_dk, btn_forgetPass;
    Button btn_DangNhap;
    EditText edt_email, edt_password;
    private HttpActivity httpActivity;
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_nhap);
        edt_email = findViewById(R.id.edt_email_DN);
        edt_password = findViewById(R.id.edt_password_DN);
        btn_dk = findViewById(R.id.btn_account);
        btn_forgetPass = findViewById(R.id.btn_forgetPass);
        btn_DangNhap = findViewById(R.id.btn_DangNhap);
        httpActivity = new HttpActivity();

        btn_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManDangNhap.this, ManDangKy.class));
            }
        });

        btn_forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManDangNhap.this, ForgotPass.class));
            }
        });
        btn_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String edt_email_user = edt_email.getText().toString();
                String edt_pass_user = edt_password.getText().toString();
                if (edt_email_user.equals("") || edt_pass_user.equals("")) {
                    Toast.makeText(ManDangNhap.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                users users = new users();
                users.setEmail(edt_email_user);
                users.setPassword(edt_pass_user);
                httpActivity.callAPI().login(users).enqueue(new Callback<Response<users>>() {
                    @Override
                    public void onResponse(Call<Response<users>> call, retrofit2.Response<Response<users>> response) {
                        if(response.isSuccessful()){
                            if(response.body().getStatus()==200){
                                Toast.makeText(ManDangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("INFO",MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token",response.body().getToken());
                                editor.putString("refreshToken",response.body().getRefreshToken());
                                editor.putString("id",response.body().getData().get_id());
                                editor.apply();
                                startActivity(new Intent(ManDangNhap.this, ManChiTietSp.class));
                            }else {
                                Toast.makeText(ManDangNhap.this, "Tên người dùng hoặc mật khẩu không chính sác", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(ManDangNhap.this, "Lỗi, Đăng nhập", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<com.example.ph36210_and103_assignment.modle.users>> call, Throwable t) {
                        Toast.makeText(ManDangNhap.this, "Tên người dùng hoặc mật khẩu không chính xác vui lòng xem lại!!!", Toast.LENGTH_SHORT).show();
                        Log.d(">>> getlistusêr", "onFailure" + t.getMessage());
                    }
                });
            }
        });
    }

}