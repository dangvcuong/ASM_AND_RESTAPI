package com.example.ph36210_and103_assignment.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ph36210_and103_assignment.R;
import com.example.ph36210_and103_assignment.hendle.Item_Cart;
import com.example.ph36210_and103_assignment.modle.CalendarS;
import com.example.ph36210_and103_assignment.modle.Cart;
import com.example.ph36210_and103_assignment.modle.Response;
import com.example.ph36210_and103_assignment.modle.users;
import com.example.ph36210_and103_assignment.servers.HttpActivity;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;

public class adapter_Cart extends RecyclerView.Adapter<adapter_Cart.viewHolder> {
    private Context context;
    private final ArrayList<Cart> listCart;
    private Item_Cart hendle;
    private HttpActivity httpActivity;
    String idUser;

    public adapter_Cart(Context context, ArrayList<Cart> listCart, Item_Cart hendle) {
        this.context = context;
        this.listCart = listCart;
        this.hendle = hendle;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_cart, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Cart cart = new Cart();
        httpActivity = new HttpActivity();
        cart = listCart.get(position);
        holder.ten_cart.setText(cart.getTen_shose_cart());
        holder.gia_cart.setText("" + cart.getGia_shose_cart() + "$");
        String loadanh = listCart.get(position).getHinh_anh_cart();
        Glide.with(context)
                .load(loadanh)
                .thumbnail(Glide.with(context).load(R.mipmap.ic_launcher))
                .into(holder.anh_Cart);


        Cart finalCart = cart;
        holder.btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo!!!");
                builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng không ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        hendle.Delete(finalCart.getId());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
        holder.btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Xác nhận thanh toán!!");
                builder.setMessage("MaSP: " + finalCart.getId_Shose_cart() + "\n" + "TenSP: " + finalCart.getTen_shose_cart() + "\n" + "Gia: " + finalCart.getGia_shose_cart());
                builder.setPositiveButton("Thanh toán", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SharedPreferences sharedPreferences = context.getSharedPreferences("INFO", Context.MODE_PRIVATE);
                        idUser = sharedPreferences.getString("id", "");
                        httpActivity.callAPI().getuserID(idUser).enqueue(new Callback<Response<users>>() {
                            @Override
                            public void onResponse(Call<Response<users>> call, retrofit2.Response<Response<users>> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getStatus() == 200) {
                                        users users = response.body().getData();
                                        if (users != null) {
                                            String anh_user = users.getHinh_anh_user();
                                            String sdt_user = users.getSdt_user();
                                            String diachi_user = users.getDia_chi_user();
                                            if (anh_user==null||sdt_user==null||diachi_user==null){
                                                Toast.makeText(context, "Vui lòng cập nhật đầy đủ thông tin người dùng để có thể thanh toán !!!", Toast.LENGTH_SHORT).show();
                                            }else {
                                                CalendarS calendar = new CalendarS();
                                                calendar.setId_cart_Calendar(finalCart.getId());
                                                calendar.setId_Shose_Calendar(finalCart.getId_Shose_cart());
                                                calendar.setGia_shose_Calendar(Integer.valueOf(String.valueOf(finalCart.getGia_shose_cart())));
                                                calendar.setTen_shose_Calendar(finalCart.getTen_shose_cart());
                                                calendar.setHinh_anh_Calendar(finalCart.getHinh_anh_cart());
                                                calendar.setId_User_Calendar(idUser);
                                                // Lấy ngày giờ thực
                                                java.util.Calendar calendar1 = Calendar.getInstance();
                                                int year = calendar1.get(Calendar.YEAR);
                                                int month = calendar1.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cần cộng thêm 1
                                                int dayOfMonth = calendar1.get(Calendar.DAY_OF_MONTH);
                                                int hour = calendar1.get(Calendar.HOUR_OF_DAY);
                                                int minute = calendar1.get(Calendar.MINUTE);
                                                int second = calendar1.get(Calendar.SECOND);
                                                String date =dayOfMonth + "/" + month + "/" + year + " |--| "+hour + ":" + minute + ":" + second;
                                                calendar.setDate_Calendar(date);
                                                httpActivity.callAPI().addCalendar(calendar).enqueue(new Callback<Response<CalendarS>>() {
                                                    @Override
                                                    public void onResponse(Call<Response<CalendarS>> call, retrofit2.Response<Response<CalendarS>> response) {
                                                        if(response.isSuccessful()){
                                                            if(response.body().getStatus()==200){
                                                                hendle.Delete(finalCart.getId());
                                                                Toast.makeText(context, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                                                            }else {
                                                                Toast.makeText(context, "Lỗi thanh toán", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Response<CalendarS>> call, Throwable t) {
                                                        Log.d(">>> ađCalendar", "onFailure" + t.getMessage());
                                                    }
                                                });
                                            }

                                        }                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Response<users>> call, Throwable t) {
                                Log.d(">> GetUser", "onFailure" + t.getMessage());
                            }
                        });

                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        Button btn_xoa, btn_payment;
        ImageView anh_Cart;
        TextView ten_cart, gia_cart;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            anh_Cart = itemView.findViewById(R.id.img_anh);
            ten_cart = itemView.findViewById(R.id.ten_SP);
            gia_cart = itemView.findViewById(R.id.gia_SP);
            btn_xoa = itemView.findViewById(R.id.btn_deleteCart);
            btn_payment = itemView.findViewById(R.id.btn_paymantCart);
        }
    }
}
