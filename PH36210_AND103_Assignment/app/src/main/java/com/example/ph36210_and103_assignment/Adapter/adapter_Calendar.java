package com.example.ph36210_and103_assignment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ph36210_and103_assignment.R;
import com.example.ph36210_and103_assignment.hendle.Item_Calendar;
import com.example.ph36210_and103_assignment.modle.CalendarS;

import java.util.ArrayList;
import java.util.Calendar;

public class adapter_Calendar extends RecyclerView.Adapter<adapter_Calendar.viewHolder>{
private Context context;
private final ArrayList<CalendarS> listCalendars;
private Item_Calendar handle;

    public adapter_Calendar(Context context, ArrayList<CalendarS> listCalendars, Item_Calendar handle) {
        this.context = context;
        this.listCalendars = listCalendars;
        this.handle = handle;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_calendar,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        CalendarS calendarS = new CalendarS();
        calendarS = listCalendars.get(position);
        holder.maThanhToan.setText("Mã thanh toán: "+calendarS.getId());
        holder.maGioHang.setText("Mã giỏ hàng: "+calendarS.getId_cart_Calendar());
        holder.maSP.setText("Mã sản phẩm: "+calendarS.getId_Shose_Calendar());
        holder.ngayGio.setText("Ngày giờ: "+calendarS.getDate_Calendar());
        holder.maUser.setText("Mã user: "+calendarS.getId_User_Calendar());
    }

    @Override
    public int getItemCount() {
        return listCalendars.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
    TextView maThanhToan, maGioHang, maSP, ngayGio,maUser;
    public viewHolder(@NonNull View itemView) {
        super(itemView);
        maThanhToan =  itemView.findViewById(R.id.tv_ma_thanh_toan);
        maGioHang = itemView.findViewById(R.id.tv_ma_gio_hang);
        maSP = itemView.findViewById(R.id.tv_ma_san_pham);
        ngayGio = itemView.findViewById(R.id.tv_thoi_gian);
        maUser = itemView.findViewById(R.id.tv_ma_user);
    }
}
}
