package com.example.ph36210_and103_assignment.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ph36210_and103_assignment.Adapter.adpater_Heart;
import com.example.ph36210_and103_assignment.ManChao;;
import com.example.ph36210_and103_assignment.R;
import com.example.ph36210_and103_assignment.hendle.Item_Heart;
import com.example.ph36210_and103_assignment.modle.Response;

import com.example.ph36210_and103_assignment.modle.SpHeart;
import com.example.ph36210_and103_assignment.modle.users;
import com.example.ph36210_and103_assignment.servers.HttpActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class frm_setting extends Fragment {
    ImageView tv_anh_user;
    TextView tv_ten_user, tv_emai_user;
    LinearLayout btn_update_user, btn_dang_xuat, btn_xem_TTCN, btn_doiMK, btn_sp_yeuthich;

    private ArrayList<users> listUses;
    private HttpActivity httpActivity;
    String idUser;
    private File file;
    ImageView updata_anh;
    private Item_Heart itemHeart;
    private ArrayList<SpHeart> listHeart;
    RecyclerView recyclerView_haer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frm_setting, null);
        tv_anh_user = view.findViewById(R.id.img_anh_user);
        tv_ten_user = view.findViewById(R.id.tv_ten_user);
        tv_emai_user = view.findViewById(R.id.tv_email_user);
        btn_update_user = view.findViewById(R.id.btn_update_user);
        btn_dang_xuat = view.findViewById(R.id.btn_Dang_Xuat);
        btn_xem_TTCN = view.findViewById(R.id.btn_xem_thong_tin_ca_nhan);
        btn_sp_yeuthich = view.findViewById(R.id.btn_sp_yt);
        btn_doiMK = view.findViewById(R.id.btn_doiMK);
        listUses = new ArrayList<>();
        listHeart = new ArrayList<>();
        httpActivity = new HttpActivity();

        itemHeart = new Item_Heart() {
            @Override
            public void Delete(String id) {
                httpActivity.callAPI().deleteHeart(id).enqueue(new Callback<Response<SpHeart>>() {
                    @Override
                    public void onResponse(Call<Response<SpHeart>> call, retrofit2.Response<Response<SpHeart>> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                httpActivity.callAPI().getListHeart().enqueue(new Callback<Response<ArrayList<SpHeart>>>() {
                                    @Override
                                    public void onResponse(Call<Response<ArrayList<SpHeart>>> call, retrofit2.Response<Response<ArrayList<SpHeart>>> response) {
                                        if (response.isSuccessful()) {
                                            if (response.body().getStatus() == 200) {
                                                listHeart = response.body().getData();
                                                adpater_Heart adpaterHeart = new adpater_Heart(getContext(), listHeart, itemHeart);
                                                recyclerView_haer.setAdapter(adpaterHeart);
                                                Toast.makeText(getContext(), "Thanh cpng", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(getContext(), "Lỗi!!!", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Response<ArrayList<SpHeart>>> call, Throwable t) {
                                        Log.d(">> GetHeart", "onFailure" + t.getMessage());
                                    }
                                });
                            } else {
                                Toast.makeText(getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Lỗi!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<SpHeart>> call, Throwable t) {
                        Log.d(">> GetHeart", "onFailure" + t.getMessage());
                    }
                });
            }

            @Override
            public void Update(String id, SpHeart spHeart) {

            }
        };

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("INFO", Context.MODE_PRIVATE);
        idUser = sharedPreferences.getString("id", "");
        httpActivity.callAPI().getuserID(idUser).enqueue(responseUser);

        btn_update_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1 = getLayoutInflater();
                View view1 = inflater1.inflate(R.layout.dialog_porson, null);
                builder.setView(view1);
                updata_anh = view1.findViewById(R.id.updata_anh_user);
                TextView ten, ho, ngay, sdt, diachi;
                ten = view1.findViewById(R.id.update_ten_user);
                ho = view1.findViewById(R.id.update_ho_user);
                ngay = view1.findViewById(R.id.updata_ngay_sinh_user);
                sdt = view1.findViewById(R.id.update_sdt_user);
                diachi = view1.findViewById(R.id.updata_dia_chi_user);
                Button btn_updata = view1.findViewById(R.id.btn_update_user);
                ImageView btn_back = view1.findViewById(R.id.btn_back);
                Dialog dialog = builder.create();
                dialog.show();

                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                updata_anh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chomseAnh();
                    }
                });

                btn_updata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestBody full_name = RequestBody.create(MediaType.parse("multipart/from-data"), ten.getText().toString() + ho.getText().toString());
                        RequestBody _ngay = RequestBody.create(MediaType.parse("multipart/from-data"), ngay.getText().toString());
                        RequestBody _sdt = RequestBody.create(MediaType.parse("multipart/from-data"), sdt.getText().toString());
                        RequestBody _diachi = RequestBody.create(MediaType.parse("multipart/from-data"), diachi.getText().toString());
                        if (ten.getText().toString().isEmpty() || ho.getText().toString().isEmpty() || ngay.getText().toString().isEmpty() || sdt.getText().toString().isEmpty() || diachi.getText().toString().isEmpty()) {
                            Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        MultipartBody.Part multipartBody;
                        if (file != null) {
                            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                            multipartBody = MultipartBody.Part.createFormData("hinh_anh_user", file.getName(), requestBody);
                        } else {
                            multipartBody = null;
                        }

                        httpActivity.callAPI().updatause(idUser, full_name, _ngay, _diachi, _sdt, multipartBody).enqueue(responseUser);
                        Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }
        });

        btn_xem_TTCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1 = getLayoutInflater();
                View view1 = inflater1.inflate(R.layout.dialog_thongtin, null);
                builder.setView(view1);
                ImageView img_anhuser = view1.findViewById(R.id.img_anh_dd);
                TextView iduser, tenuser, diachiuser, ngayuser, emaiuser, sdtuser;
                iduser = view1.findViewById(R.id.tv_id_user);
                tenuser = view1.findViewById(R.id.tv_ten_user);
                diachiuser = view1.findViewById(R.id.tv_quequan_user);
                ngayuser = view1.findViewById(R.id.tv_ngaysinh_user);
                emaiuser = view1.findViewById(R.id.tv_email_user);
                sdtuser = view1.findViewById(R.id.tv_sdt_user);
                httpActivity.callAPI().getuserID(idUser).enqueue(new Callback<Response<users>>() {
                    @Override
                    public void onResponse(Call<Response<users>> call, retrofit2.Response<Response<users>> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                users users = response.body().getData();
                                if (users != null) {
                                    String email_user = users.getEmail();
                                    String ten_user = users.getFullname();
                                    String anh_user = users.getHinh_anh_user();
                                    String sdt_user = users.getSdt_user();
                                    String id_user = users.get_id();
                                    String diachi_user = users.getDia_chi_user();
                                    String nga_user = users.getNgay_sinh_user();

                                    Glide.with(getActivity())
                                            .load(anh_user)
                                            .thumbnail(Glide.with(getActivity()).load(R.mipmap.ic_launcher))
                                            .into(img_anhuser);
                                    iduser.setText("Mã user: " + id_user);
                                    emaiuser.setText("Email: " + email_user);
                                    tenuser.setText("Họ tên: " + ten_user);
                                    sdtuser.setText("SDT: " + sdt_user);
                                    diachiuser.setText("Địa chỉ: " + diachi_user);
                                    ngayuser.setText("Ngày sinh: " + nga_user);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<users>> call, Throwable t) {

                    }
                });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

        btn_doiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1 = getLayoutInflater();
                View view1 = inflater1.inflate(R.layout.dialog_doimk, null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();
                EditText edt_passCU, edt_passMoi, edt_XacNhan;
                Button btn_xacnhan = view1.findViewById(R.id.btn_xacnhan);
                edt_passCU = view1.findViewById(R.id.edt_pass_cu);
                edt_passMoi = view1.findViewById(R.id.edt_pass_moi);
                edt_XacNhan = view1.findViewById(R.id.edt_xan_nhan);

                btn_xacnhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        httpActivity.callAPI().getuserID(idUser).enqueue(new Callback<Response<users>>() {
                            @Override
                            public void onResponse(Call<Response<users>> call, retrofit2.Response<Response<users>> response) {
                                if (response.isSuccessful()) {
                                    if (response.body().getStatus() == 200) {
                                        String cu = edt_passCU.getText().toString();
                                        String moi = edt_passMoi.getText().toString();
                                        String xacNhan = edt_XacNhan.getText().toString();
                                        users users1 = response.body().getData();
                                        String passcu = users1.getPassword();
                                        Log.e("PassCU", "Pas" + passcu);
                                        if (cu.isEmpty() || moi.isEmpty() || xacNhan.isEmpty()) {
                                            Toast.makeText(getActivity(), "Vui lòng không để trống dữ liệu!!!", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (!cu.equals(passcu)) {
                                                Toast.makeText(getActivity(), "Mật khẩu cũ không đúng!!!", Toast.LENGTH_SHORT).show();
                                            } else {
                                                if (!xacNhan.equals(moi)) {
                                                    Toast.makeText(getActivity(), "Xác nhận mật khẩu sai!!!", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    RequestBody mk_moi = RequestBody.create(MediaType.parse("multipart/from-data"), moi);
                                                    httpActivity.callAPI().doiPass(idUser, mk_moi).enqueue(new Callback<Response<com.example.ph36210_and103_assignment.modle.users>>() {
                                                        @Override
                                                        public void onResponse(Call<Response<com.example.ph36210_and103_assignment.modle.users>> call, retrofit2.Response<Response<com.example.ph36210_and103_assignment.modle.users>> response) {
                                                            if (response.isSuccessful()) {
                                                                if (response.body().getStatus() == 200) {
                                                                    Toast.makeText(getActivity(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(getActivity(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(getActivity(), "Lỗi, Đổi mật khẩu", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Response<com.example.ph36210_and103_assignment.modle.users>> call, Throwable t) {
                                                            Log.d(">> Dôi mật khẩu", "onFailure" + t.getMessage());
                                                        }
                                                    });

                                                }
                                            }
                                        }


//                                        RequestBody passMoi = RequestBody.create(MediaType.parse("multipart/from-data"),moi);
//                                        httpActivity.callAPI().doiPass(idUser,passMoi).enqueue(responseUser);

                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Response<users>> call, Throwable t) {
                                Log.d(">> GetUser", "onFailure" + t.getMessage());
                            }
                        });
                    }
                });
            }
        });

        btn_sp_yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater1 = getLayoutInflater();
                View view1 = inflater1.inflate(R.layout.dialog_sp_hear, null);
                builder.setView(view1);
                Dialog dialog = builder.create();
                dialog.show();
                ImageView btn_back = view1.findViewById(R.id.btn_hear_back);
               recyclerView_haer = view1.findViewById(R.id.rcv_hear);

                recyclerView_haer.setLayoutManager(new LinearLayoutManager(getActivity()));
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
                recyclerView_haer.addItemDecoration(dividerItemDecoration);

                httpActivity.callAPI().getHeartIdUser(idUser).enqueue(new Callback<Response<ArrayList<SpHeart>>>() {
                    @Override
                    public void onResponse(Call<Response<ArrayList<SpHeart>>> call, retrofit2.Response<Response<ArrayList<SpHeart>>> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus() == 200) {
                                listHeart = response.body().getData();
                                adpater_Heart adpaterHeart = new adpater_Heart(getContext(), listHeart, itemHeart);
                                recyclerView_haer.setAdapter(adpaterHeart);
                            } else {
                                Toast.makeText(getContext(), "Loi", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Loi,!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<ArrayList<SpHeart>>> call, Throwable t) {
                        Log.d(">> GetUser", "onFailure" + t.getMessage());
                    }
                });


                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });

        btn_dang_xuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông báo !");
                builder.setMessage("Bạn có muốn đăng xuất không ?");
                builder.setPositiveButton("Đăng xuất", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), ManChao.class));
                        Toast.makeText(getActivity(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
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

        return view;
    }

    private View.OnClickListener chomseAnh() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            getImage.launch(intent);
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        return null;
    }

    ActivityResultLauncher<Intent> getImage = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        Intent data = o.getData();
                        Uri uri = data.getData();
                        file = createFileFromUri(uri, "hinh_anh_ph36210");
                        Glide.with(getActivity())
                                .load(file)
                                .thumbnail(Glide.with(getActivity()).load(R.mipmap.ic_launcher))
                                .centerCrop()
                                .circleCrop()
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(updata_anh);
                    }
                }
            });

    private File createFileFromUri(Uri path, String name) {
        File _file = new File(getActivity().getCacheDir(), name + ".png");
        try {
            InputStream in = getActivity().getContentResolver().openInputStream(path);
            OutputStream out = new FileOutputStream(_file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
            return _file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    Callback<Response<users>> responseUser = new Callback<Response<users>>() {
        @Override
        public void onResponse(Call<Response<users>> call, retrofit2.Response<Response<users>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    users users = response.body().getData();
                    if (users != null) {
                        String email_user = users.getEmail();
                        String ten_user = users.getFullname();
                        String anh_user = users.getHinh_anh_user();
                        String sdt_user = users.getSdt_user();
                        String id_user = users.get_id();
                        String diachi_user = users.getDia_chi_user();

                        Glide.with(getActivity())
                                .load(anh_user)
                                .thumbnail(Glide.with(getActivity()).load(R.mipmap.ic_launcher))
                                .into(tv_anh_user);
                        tv_emai_user.setText(email_user);
                        tv_ten_user.setText(ten_user);
                    }
                }
            }
        }

        @Override
        public void onFailure(Call<Response<users>> call, Throwable t) {
            Log.d(">> GetUser", "onFailure" + t.getMessage());
        }
    };


}