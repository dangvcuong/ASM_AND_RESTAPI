package com.example.ph36210_and103_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.ph36210_and103_assignment.fragment.frm_Home;
import com.example.ph36210_and103_assignment.fragment.frm_cart;
import com.example.ph36210_and103_assignment.fragment.frm_heart;
import com.example.ph36210_and103_assignment.fragment.frm_setting;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ManChiTietSp extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    FragmentContainerView fragmentContainerView;
    FragmentManager manager ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_chi_tiet_sp);

        frm_cart frmCart = new frm_cart();
        frm_heart frmHeart = new frm_heart();
        frm_Home frmHome = new frm_Home();
        frm_setting frmSetting = new frm_setting();
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fragmentContainerView = findViewById(R.id.fcv_ctsp);
        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.fcv_ctsp,frmHome).commit();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_home) {
                    relacFrm(frmHome);
                } else if (item.getItemId()==R.id.menu_cart) {
                    relacFrm(frmCart);
                } else if (item.getItemId()==R.id.menu_like) {
                    relacFrm(frmHeart);
                }else if(item.getItemId()==R.id.menu_setting){
                    relacFrm(frmSetting);
                }else {
                    Toast.makeText(ManChiTietSp.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }
    public void relacFrm(Fragment fragment) {
        manager.beginTransaction().replace(R.id.fcv_ctsp, fragment).commit();
    }
}