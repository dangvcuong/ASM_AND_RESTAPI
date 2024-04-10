package com.example.ph36210_and103_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ManDangKy extends AppCompatActivity {
    TextView btn_dn;
    EditText edt_email, edt_password, edt_fullname;
    Button btn_dk;
    private HttpActivity httpActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_dang_ky);
        edt_email = findViewById(R.id.edt_email_dk);
        edt_password = findViewById(R.id.edt_pass_dk);
        edt_fullname = findViewById(R.id.edt_fullname_dk);
        btn_dn = findViewById(R.id.btn_login);
        btn_dk = findViewById(R.id.btn_dk);
        httpActivity = new HttpActivity();

        btn_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestBody _email = RequestBody.create(MediaType.parse("multipart/from-data"), edt_email.getText().toString());
                RequestBody _password = RequestBody.create(MediaType.parse("multipart/from-data"), edt_password.getText().toString());
                RequestBody _fullname = RequestBody.create(MediaType.parse("multipart/from-data"), edt_fullname.getText().toString());

                if (edt_email.getText().toString().equals("") || edt_password.getText().toString().equals("") || edt_fullname.getText().toString().equals("")) {
                    Toast.makeText(ManDangKy.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                httpActivity.callAPI().register(_email, _password, _fullname).enqueue(responseUser);
                Toast.makeText(ManDangKy.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ManDangKy.this, ManDangNhap.class));
            }
        });

        btn_dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManDangKy.this, ManDangNhap.class));
            }
        });
    }

    Callback<Response<users>> responseUser = new Callback<Response<users>>() {
        @Override
        public void onResponse(Call<Response<users>> call, retrofit2.Response<Response<users>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(ManDangKy.this, "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                    Log.e("THanhCOng", "DanhSach");
                    startActivity(new Intent(ManDangKy.this, ManDangNhap.class));
                }
            }
        }

        @Override
        public void onFailure(Call<Response<users>> call, Throwable t) {
            Log.d(">> GetUser", "onFailure" + t.getMessage());
        }
    };

}