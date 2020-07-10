package com.destinyapp.absensi.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.destinyapp.absensi.API.ApiRequest;
import com.destinyapp.absensi.API.RetroServer;
import com.destinyapp.absensi.Activity.AbsenActivity;
import com.destinyapp.absensi.Activity.DataAbsensiActivity;
import com.destinyapp.absensi.Model.Method;
import com.destinyapp.absensi.Model.Response.DataModel;
import com.destinyapp.absensi.Model.ResponseModel;
import com.destinyapp.absensi.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterAbsen extends RecyclerView.Adapter<AdapterAbsen.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    String tanggal;
    Dialog myDialog;

    String username,nama,email,profile,alamat,level;
    Method method;
    public AdapterAbsen (Context ctx,List<DataModel> mList,String tanggal){
        this.ctx = ctx;
        this.mList = mList;
        this.tanggal = tanggal;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_absent,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAbsen.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        method=new Method();
        holderData.nama.setText(dm.getNama_karyawan());
        holderData.username.setText(dm.getUsername());
        holderData.divisi.setText(dm.getDivisi());
        if (dm.getLevel().equals("1")){
            holderData.level.setText("Admin");
        }else{
            holderData.level.setText("Member");
        }
        Checker(holderData.masuk,holderData.izin,holderData.alpa,dm.getId_karyawan());

        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView nama,username,level,divisi;
        LinearLayout masuk,izin,alpa;
        DataModel dm;
        HolderData(View v){
            super(v);
            nama=v.findViewById(R.id.tvNama);
            username=v.findViewById(R.id.tvUsername);
            divisi=v.findViewById(R.id.tvDivisi);
            level=v.findViewById(R.id.tvAdmin);
            masuk=v.findViewById(R.id.linearMasuk);
            izin=v.findViewById(R.id.linearIzin);
            alpa=v.findViewById(R.id.linearTidakMasuk);
        }
    }
    private void Checker(final LinearLayout masuk, final LinearLayout izin, final LinearLayout alpa, final String id){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Log = api.CheckAbsen(id,tanggal);
        Log.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, final Response<ResponseModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        if (response.body().getData().get(0).status.equals("Masuk")){
                            masuk.setBackgroundResource(R.drawable.button_rounded_succes);
                        }else if (response.body().getData().get(0).status.equals("Izin")){
                            izin.setBackgroundResource(R.drawable.button_rounded_succes);
                        }else{
                            alpa.setBackgroundResource(R.drawable.button_rounded_succes);
                        }
                        masuk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!response.body().getData().get(0).status.equals("Masuk")){
                                    Edit(response.body().getData().get(0).getId_absensi(),"Masuk");
                                }
                            }
                        });
                        izin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!response.body().getData().get(0).status.equals("Izin")){
                                    Edit(response.body().getData().get(0).getId_absensi(),"Izin");
                                }
                            }
                        });
                        alpa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!response.body().getData().get(0).status.equals("Tanpa Keterangan")){
                                    Edit(response.body().getData().get(0).getId_absensi(),"Tanpa Keterangan");
                                }
                            }
                        });
                    }else{
                        masuk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Default(masuk,izin,alpa);
                                masuk.setBackgroundResource(R.drawable.button_rounded_succes);
                                Input(id,"Masuk");
                            }
                        });
                        izin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Default(masuk,izin,alpa);
                                izin.setBackgroundResource(R.drawable.button_rounded_succes);
                                Input(id,"Izin");
                            }
                        });
                        alpa.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Default(masuk,izin,alpa);
                                alpa.setBackgroundResource(R.drawable.button_rounded_succes);
                                Input(id,"Tanpa Keterangan");
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void ChangeActivity(){
        Intent intent = new Intent(ctx, AbsenActivity.class);
        ctx.startActivity(intent);
        ((Activity) ctx).finish();
    }
    private void Edit(String id,String status){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Log = api.UpdateAbsen(id,status);
        Log.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        ChangeActivity();
                    }else{
                        Toast.makeText(ctx, "Terjadi kesalahan "+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(ctx, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void Input(String id,String status){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Log = api.InsertAbsen(id,status,tanggal);
        Log.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        ChangeActivity();
                    }else{
                        Toast.makeText(ctx, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(ctx, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void Default(final LinearLayout masuk, final LinearLayout izin, final LinearLayout alpa){
        masuk.setBackgroundResource(R.drawable.button_rounded_primary);
        izin.setBackgroundResource(R.drawable.button_rounded_primary);
        alpa.setBackgroundResource(R.drawable.button_rounded_primary);
    }
}

