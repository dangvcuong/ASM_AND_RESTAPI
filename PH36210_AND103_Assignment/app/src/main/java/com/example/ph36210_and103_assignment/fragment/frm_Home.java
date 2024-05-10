package com.example.ph36210_and103_assignment.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ph36210_and103_assignment.Adapter.adapter_SP;
import com.example.ph36210_and103_assignment.R;
import com.example.ph36210_and103_assignment.modle.Response;
import com.example.ph36210_and103_assignment.modle.Shose;
import com.example.ph36210_and103_assignment.modle.users;
import com.example.ph36210_and103_assignment.servers.HttpActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class frm_Home extends Fragment {
    RecyclerView recyclerView, recyclerView1;
    ArrayList<Shose> list;
    adapter_SP adapterSp;
    private HttpActivity httpActivity;
    ImageView btn_search, anh_user_home;
    String idUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frm__home, null);
        recyclerView = view.findViewById(R.id.rcv_SP);
        btn_search = view.findViewById(R.id.btn_search);
        anh_user_home = view.findViewById(R.id.anh_nguoi_dung);

        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("INFO", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString("id", "");
        httpActivity = new HttpActivity();
        httpActivity.callAPI().getListShose().enqueue(getListShose);

        httpActivity.callAPI().getuserID(idUser).enqueue(getOneUser);

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSearch();
            }
        });


        return view;
    }

    private View.OnClickListener showDialogSearch() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_search, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams params = window.getAttributes();
            // Xác định vị trí của cửa sổ dialog ở đây
            params.gravity = Gravity.TOP;
            window.setAttributes(params);
        }

        EditText search = view.findViewById(R.id.edt_search);
        ImageView btn_search_shose = view.findViewById(R.id.btn_search_shose);
        ImageView btn_back = view.findViewById(R.id.btn_search_back);
        RecyclerView recyclerView2 = view.findViewById(R.id.rcv_search_shose);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_search_shose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameShose = search.getText().toString();
                if (nameShose.isEmpty()) {
                    Toast.makeText(getActivity(), "Vui lòng nhập sản phẩm cần tìm", Toast.LENGTH_SHORT).show();
                } else {
                    httpActivity.callAPI().searchShose(nameShose).enqueue(new Callback<Response<ArrayList<Shose>>>() {
                        @Override
                        public void onResponse(Call<Response<ArrayList<Shose>>> call, retrofit2.Response<Response<ArrayList<Shose>>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus() == 200) {
                                    ArrayList<Shose> searchResults = response.body().getData();

                                    // Cập nhật danh sách sản phẩm trong adapter
                                    adapter_SP adapterSp1 = new adapter_SP(getContext(), searchResults);

                                    recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
                                    DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                                    recyclerView2.addItemDecoration(dividerItemDecoration1);
                                    recyclerView2.setAdapter(adapterSp1);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Response<ArrayList<Shose>>> call, Throwable t) {

                        }
                    });

                }


            }
        });

        return null;
    }

    Callback<Response<ArrayList<Shose>>> getListShose = new Callback<Response<ArrayList<Shose>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Shose>>> call, retrofit2.Response<Response<ArrayList<Shose>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    list = response.body().getData();
                    adapterSp = new adapter_SP(getContext(), list);
                    recyclerView.setAdapter(adapterSp);
                } else {
                    Toast.makeText(getContext(), "list" + list, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "list" + list, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Shose>>> call, Throwable t) {
            Log.e(">>> GetListShose", "onFailure" + t.getMessage());
        }
    };

    Callback<Response<users>> getOneUser = new Callback<Response<users>>() {
        @Override
        public void onResponse(Call<Response<users>> call, retrofit2.Response<Response<users>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus()==200){
                    users users = response.body().getData();
                    if(users!=null){
                        String anh =  users.getHinh_anh_user();
                        Glide.with(getActivity())
                                .load(anh)
                                .thumbnail(Glide.with(getActivity()).load(R.mipmap.ic_launcher))
                                .into(anh_user_home);
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<Response<users>> call, Throwable t) {

        }
    };
}
