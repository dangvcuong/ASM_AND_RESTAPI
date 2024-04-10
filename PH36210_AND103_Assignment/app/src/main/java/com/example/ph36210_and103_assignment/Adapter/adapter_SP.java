package com.example.ph36210_and103_assignment.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import com.example.ph36210_and103_assignment.hendle.Item_Handle;
import com.example.ph36210_and103_assignment.modle.Cart;
import com.example.ph36210_and103_assignment.modle.Response;
import com.example.ph36210_and103_assignment.modle.Shose;
import com.example.ph36210_and103_assignment.modle.SpHeart;
import com.example.ph36210_and103_assignment.servers.HttpActivity;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class adapter_SP extends RecyclerView.Adapter<adapter_SP.viewHolder> {
    private final Context context;
    private ArrayList<Shose> list;
    private HttpActivity httpActivity;
    String idUser;

    public adapter_SP(Context context, ArrayList<Shose> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_shoe, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Shose shose = new Shose();
        shose = list.get(position);
        httpActivity = new HttpActivity();
        SharedPreferences sharedPreferences = context.getSharedPreferences("INFO", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString("id", "");
        holder.tenSP.setText(shose.getShoseName());
        holder.giaSP.setText("" + shose.getPrice() + "$");
        String loadanh = list.get(position).getImage_anh();
        Glide.with(context)
                .load(loadanh)
                .thumbnail(Glide.with(context).load(R.mipmap.ic_launcher))
                .into(holder.anh);

        Shose finalShose = shose;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_sp, null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();

                TextView ten_shose, gia_shose, mo_ta;
                ten_shose = view.findViewById(R.id.ten_SP);
                gia_shose = view.findViewById(R.id.gia_SP);
                mo_ta = view.findViewById(R.id.mo_ta);
                ImageView anh_sp = view.findViewById(R.id.Img_Hinh_Anh);
                ImageView btn_back = view.findViewById(R.id.btn_back);
                Button btn_addCart = view.findViewById(R.id.btn_addCart);
                Button btn_addFrvorit = view.findViewById(R.id.btn_addFrvorit);

                ten_shose.setText(finalShose.getShoseName());
                gia_shose.setText("" + finalShose.getPrice() + "$");
                mo_ta.setText(finalShose.getDescription());
                String loadanh = finalShose.getImage_anh();
                Glide.with(context)
                        .load(loadanh)
                        .thumbnail(Glide.with(context).load(R.mipmap.ic_launcher))
                        .into(anh_sp);

                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_addCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cart cart = new Cart();
                        cart.setId_Shose_cart(finalShose.getId());
                        cart.setTen_shose_cart(finalShose.getShoseName());
                        cart.setGia_shose_cart(finalShose.getPrice());
                        cart.setHinh_anh_cart(finalShose.getImage_anh());
                        cart.setId_User_cart(idUser);
                        httpActivity.callAPI().addCart(cart).enqueue(ThemCart);
                        dialog.dismiss();
                    }
                });
                btn_addFrvorit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SpHeart spHeart = new SpHeart();
                        spHeart.setId_User_heart(idUser);
                        spHeart.setId_Shose_heart(finalShose.getId());
                        spHeart.setShose_Name_heart(finalShose.getShoseName());
                        spHeart.setPrice_heart(finalShose.getPrice());
                        spHeart.setDescription_heart(finalShose.getDescription());
                        spHeart.setImage_anh_heart(finalShose.getImage_anh());
                        httpActivity.callAPI().addHeart(spHeart).enqueue(new Callback<Response<SpHeart>>() {
                            @Override
                            public void onResponse(Call<Response<SpHeart>> call, retrofit2.Response<Response<SpHeart>> response) {
                                if(response.isSuccessful()){
                                    if(response.body().getStatus()==200){
                                        Toast.makeText(context, "Đã thêm vào yêu thích", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }else {
                                        Toast.makeText(context, "Lỗi,!!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(context, "Lỗi,!!!!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Response<SpHeart>> call, Throwable t) {

                            }
                        });
                    }
                });
            }
        });

    }

    Callback<Response<Cart>> ThemCart = new Callback<Response<Cart>>() {
        @Override
        public void onResponse(Call<Response<Cart>> call, retrofit2.Response<Response<Cart>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    Toast.makeText(context, "Them thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Them that bai", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Cart>> call, Throwable t) {
            Log.d(">>> ađCart", "onFailure" + t.getMessage());
        }
    };


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView tim, anh;
        TextView tenSP, giaSP;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tim = itemView.findViewById(R.id.icon_tim);
            anh = itemView.findViewById(R.id.img_anh);
            tenSP = itemView.findViewById(R.id.ten_SP);
            giaSP = itemView.findViewById(R.id.gia_SP);
        }
    }


}
