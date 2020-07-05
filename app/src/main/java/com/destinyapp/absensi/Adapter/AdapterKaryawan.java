package com.destinyapp.absensi.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.destinyapp.absensi.Model.Method;
import com.destinyapp.absensi.Model.Response.DataModel;
import com.destinyapp.absensi.R;

import java.util.List;

public class AdapterKaryawan extends RecyclerView.Adapter<AdapterKaryawan.HolderData> {
    private List<DataModel> mList;
    private Context ctx;
    Dialog myDialog;

    String username,nama,email,profile,alamat,level;
    Method method;
    public AdapterKaryawan (Context ctx,List<DataModel> mList){
        this.ctx = ctx;
        this.mList = mList;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_karyawan,viewGroup,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKaryawan.HolderData holderData, int posistion) {
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
        holderData.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holderData.dm=dm;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class HolderData extends RecyclerView.ViewHolder{
        TextView nama,username,level,divisi;
        LinearLayout detail;
        DataModel dm;
        HolderData(View v){
            super(v);
            nama=v.findViewById(R.id.tvNama);
            username=v.findViewById(R.id.tvUsername);
            divisi=v.findViewById(R.id.tvDivisi);
            level=v.findViewById(R.id.tvAdmin);
            detail=v.findViewById(R.id.linearDetail);
        }
    }

}

