package com.example.ph36210_and103_assignment.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ph36210_and103_assignment.Adapter.adapter_Cart;
import com.example.ph36210_and103_assignment.R;
import com.example.ph36210_and103_assignment.hendle.Item_Cart;
import com.example.ph36210_and103_assignment.modle.Cart;
import com.example.ph36210_and103_assignment.modle.Response;
import com.example.ph36210_and103_assignment.servers.HttpActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class frm_cart extends Fragment {
    RecyclerView recyclerView;
    private HttpActivity httpActivity;
    ArrayList<Cart> listCart;
    adapter_Cart adapterCart;
    private Item_Cart hendle;
    String idUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frm_cart, null);
        recyclerView = view.findViewById(R.id.rcv_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("INFO", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString("id", "");

        httpActivity = new HttpActivity();
        httpActivity.callAPI().getCartIdUser(idUser).enqueue(getCartIdUser);

        hendle = new Item_Cart() {
            @Override
            public void Delete(String id) {
            httpActivity.callAPI().deleteCart(id).enqueue(ThemCart);
            }

            @Override
            public void Update(String id, Cart cart) {

            }
        };
        return view;
    }
Callback<Response<ArrayList<Cart>>> getCartIdUser = new Callback<Response<ArrayList<Cart>>>() {
    @Override
    public void onResponse(Call<Response<ArrayList<Cart>>> call, retrofit2.Response<Response<ArrayList<Cart>>> response) {
        if (response.isSuccessful()) {
            if (response.body().getStatus() == 200) {
                listCart = response.body().getData();
                adapterCart = new adapter_Cart(getContext(), listCart,hendle);
                recyclerView.setAdapter(adapterCart);
            } else {
                Toast.makeText(getContext(), "getTC" + listCart, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "getTC" + listCart, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<Response<ArrayList<Cart>>> call, Throwable t) {
        Toast.makeText(getContext(), "getTC" + listCart, Toast.LENGTH_SHORT).show();
    }
};



    Callback<Response<Cart>> ThemCart = new Callback<Response<Cart>>() {
        @Override
        public void onResponse(Call<Response<Cart>> call, retrofit2.Response<Response<Cart>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    httpActivity.callAPI().getCartIdUser(idUser).enqueue(getCartIdUser);
                } else {
                    Toast.makeText(getContext(), "Them that bai", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Cart>> call, Throwable t) {
            Log.d(">>> ađCart", "onFailure" + t.getMessage());
        }
    };


}