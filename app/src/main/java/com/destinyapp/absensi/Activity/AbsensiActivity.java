package com.destinyapp.absensi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.destinyapp.absensi.API.ApiRequest;
import com.destinyapp.absensi.API.RetroServer;
import com.destinyapp.absensi.Adapter.AdapterAbsen;
import com.destinyapp.absensi.Model.Response.DataModel;
import com.destinyapp.absensi.Model.ResponseModel;
import com.destinyapp.absensi.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsensiActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private List<DataModel> mItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);
        recyclerView=findViewById(R.id.recycler);
        Data();
    }

    private void Data(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Log = api.Karyawan();
        Log.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        String thisDay = dateFormat.format(date);
                        recyclerView.setLayoutManager(new GridLayoutManager(AbsensiActivity.this, 2));
                        mItems=response.body().getData();
                        AdapterAbsen gridAdapter = new AdapterAbsen(AbsensiActivity.this,mItems,thisDay);
                        recyclerView.setAdapter(gridAdapter);
                    }else{

                    }
                }catch (Exception e){
                    Toast.makeText(AbsensiActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(AbsensiActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}