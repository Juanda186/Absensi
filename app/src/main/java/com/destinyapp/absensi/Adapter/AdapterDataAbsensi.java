package com.destinyapp.absensi.Adapter;

import android.app.Dialog;
import android.content.Context;
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
import com.destinyapp.absensi.Model.Method;
import com.destinyapp.absensi.Model.Response.DataModel;
import com.destinyapp.absensi.Model.ResponseModel;
import com.destinyapp.absensi.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterDataAbsensi extends RecyclerView.Adapter<AdapterDataAbsensi.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    private String thisday;
    Dialog myDialog;

    String username,nama,email,profile,alamat,level;
    Method method;
    public AdapterDataAbsensi (Context ctx,List<DataModel> mList,String thisday){
        this.ctx = ctx;
        this.mList = mList;
        this.thisday = thisday;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_absensi,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataAbsensi.HolderData holderData, int posistion) {
        DataModel dm = mList.get(posistion);
        method=new Method();
        holderData.nama.setText(dm.getNama_karyawan());
        holderData.divisi.setText(dm.getDivisi());
        Checker(holderData.status,dm.getId_karyawan());
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView nama,divisi,status;
        DataModel dm;
        HolderData(View v){
            super(v);
            nama=v.findViewById(R.id.tvNama);
            divisi=v.findViewById(R.id.tvDivisi);
            status=v.findViewById(R.id.tvStatus);
        }
    }
    private void Checker(final TextView status, String username){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Log = api.CheckAbsen(username,thisday);
        Toast.makeText(ctx, thisday, Toast.LENGTH_SHORT).show();
        Log.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        status.setText(response.body().getData().get(0).status);
                    }else{
                        status.setText("Tanpa Keterangan");
                    }
                }catch (Exception e){
                    Toast.makeText(ctx, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(ctx, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


