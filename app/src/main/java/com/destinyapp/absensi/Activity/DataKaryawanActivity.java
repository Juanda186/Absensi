package com.destinyapp.absensi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.destinyapp.absensi.API.ApiRequest;
import com.destinyapp.absensi.API.RetroServer;
import com.destinyapp.absensi.Adapter.AdapterKaryawan;
import com.destinyapp.absensi.Model.Response.DataModel;
import com.destinyapp.absensi.Model.ResponseModel;
import com.destinyapp.absensi.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataKaryawanActivity extends AppCompatActivity {
    LinearLayout available;
    RecyclerView recyclerView;
    private List<DataModel> mItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_karyawan);
        available = findViewById(R.id.linearAvailable);
        recyclerView = findViewById(R.id.recycler);
        available.setVisibility(View.GONE);
        Logic();
    }

    private void Logic(){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Log = api.Karyawan();
        Log.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        available.setVisibility(View.GONE);
                        recyclerView.setLayoutManager(new GridLayoutManager(DataKaryawanActivity.this, 1));
                        mItems=response.body().getData();
                        AdapterKaryawan gridAdapter = new AdapterKaryawan(DataKaryawanActivity.this,mItems);
                        recyclerView.setAdapter(gridAdapter);
                    }else{
                        available.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    Toast.makeText(DataKaryawanActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(DataKaryawanActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DataKaryawanActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
