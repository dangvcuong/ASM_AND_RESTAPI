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
import com.example.ph36210_and103_assignment.hendle.Item_Heart;
import com.example.ph36210_and103_assignment.modle.Cart;
import com.example.ph36210_and103_assignment.modle.Response;
import com.example.ph36210_and103_assignment.modle.SpHeart;
import com.example.ph36210_and103_assignment.servers.HttpActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class adpater_Heart extends RecyclerView.Adapter<adpater_Heart.viewHolder> {
    private Context context;
    private final ArrayList<SpHeart> listheart;
    private Item_Heart itemHeart;
    private HttpActivity httpActivity;
String idUser;
    public adpater_Heart(Context context, ArrayList<SpHeart> listheart, Item_Heart itemHeart) {
        this.context = context;
        this.listheart = listheart;
        this.itemHeart = itemHeart;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_heart, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("INFO", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString("id", "");
        httpActivity = new HttpActivity();
        SpHeart spHeart = new SpHeart();
        spHeart = listheart.get(position);
        holder.tv_name_haert.setText(spHeart.getShose_Name_heart());
        holder.tv_gia_heart.setText("" + spHeart.getPrice_heart());
        String loadanh = listheart.get(position).getImage_anh_heart();
        Glide.with(context)
                .load(loadanh)
                .thumbnail(Glide.with(context).load(R.mipmap.ic_launcher))
                .into(holder.anh_Heart);


        SpHeart finalSpHeart = spHeart;
        holder.btn_deletehaert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Cảnh báo!!!");
                builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi yêu thích không ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemHeart.Delete(finalSpHeart.getId());
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

        holder.btn_addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cart cart = new Cart();
                cart.setId_Shose_cart(finalSpHeart.getId_Shose_heart());
                cart.setTen_shose_cart(finalSpHeart.getShose_Name_heart());
                cart.setGia_shose_cart(finalSpHeart.getPrice_heart());
                cart.setHinh_anh_cart(finalSpHeart.getImage_anh_heart());
                cart.setId_User_cart(idUser);
                httpActivity.callAPI().addCart(cart).enqueue(new Callback<Response<Cart>>() {
                    @Override
                    public void onResponse(Call<Response<Cart>> call, retrofit2.Response<Response<Cart>> response) {
                        if (response.isSuccessful()){
                            if(response.body().getStatus()==200){
                                Toast.makeText(context, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(context, "Lỗi!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<Cart>> call, Throwable t) {
                        Log.d(">>> ađCart", "onFailure" + t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return listheart.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView anh_Heart;
        TextView tv_name_haert;
        TextView tv_gia_heart;
        Button btn_deletehaert, btn_addCart;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            anh_Heart = itemView.findViewById(R.id.img_anh_hear);
            tv_name_haert = itemView.findViewById(R.id.ten_SP_heart);
            tv_gia_heart = itemView.findViewById(R.id.gia_SP_hear);
            btn_addCart = itemView.findViewById(R.id.btn_paymantCart_Heart);
            btn_deletehaert = itemView.findViewById(R.id.btn_deleteCart_heart);
        }
    }
}
