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

import com.example.ph36210_and103_assignment.Adapter.adapter_Calendar;
import com.example.ph36210_and103_assignment.Adapter.adapter_Cart;
import com.example.ph36210_and103_assignment.R;
import com.example.ph36210_and103_assignment.hendle.Item_Calendar;
import com.example.ph36210_and103_assignment.hendle.Item_Cart;
import com.example.ph36210_and103_assignment.modle.CalendarS;
import com.example.ph36210_and103_assignment.modle.Cart;
import com.example.ph36210_and103_assignment.modle.Response;
import com.example.ph36210_and103_assignment.servers.HttpActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;


public class frm_heart extends Fragment {

    RecyclerView recyclerView;
    private HttpActivity httpActivity;
    ArrayList<CalendarS> listCalendar;
    adapter_Calendar adapterCalendar;
    private Item_Calendar hendle;
    String idUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frm_heart, null);
        recyclerView = view.findViewById(R.id.rcv_calendar);
        listCalendar = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("INFO", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString("id", "");
        httpActivity = new HttpActivity();
        httpActivity.callAPI().getCalendarIdUser(idUser).enqueue(getGetListCalendarIDUser);
        return view;
    }

    Callback<Response<ArrayList<CalendarS>>> getGetListCalendarIDUser = new Callback<Response<ArrayList<CalendarS>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<CalendarS>>> call, retrofit2.Response<Response<ArrayList<CalendarS>>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus()==200){
                    listCalendar = response.body().getData();
                    adapterCalendar = new adapter_Calendar(getContext(),listCalendar,hendle);
                    recyclerView.setAdapter(adapterCalendar);
                }else {
                    Toast.makeText(getContext(), "listCalendar"+listCalendar, Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getContext(), "listCalendar"+listCalendar, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<CalendarS>>> call, Throwable t) {
            Toast.makeText(getContext(), "listCalendar"+listCalendar, Toast.LENGTH_SHORT).show();
        }
    };
    Callback<Response<ArrayList<CalendarS>>> getListCalendar = new Callback<Response<ArrayList<CalendarS>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<CalendarS>>> call, retrofit2.Response<Response<ArrayList<CalendarS>>> response) {
            if(response.isSuccessful()){
                if(response.body().getStatus()==200){
                    listCalendar = response.body().getData();
                    adapterCalendar = new adapter_Calendar(getContext(),listCalendar,hendle);
                    recyclerView.setAdapter(adapterCalendar);
                }else {
                    Toast.makeText(getContext(), "listCalendar"+listCalendar, Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getContext(), "listCalendar"+listCalendar, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<CalendarS>>> call, Throwable t) {
            Log.d(">>> getLisstCalendar", "onFailure" + t.getMessage());
        }
    };
}