package com.destinyapp.absensi.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.absensi.API.ApiRequest;
import com.destinyapp.absensi.API.RetroServer;
import com.destinyapp.absensi.Adapter.AdapterDataAbsensi;
import com.destinyapp.absensi.Adapter.AdapterKaryawan;
import com.destinyapp.absensi.Model.Method;
import com.destinyapp.absensi.Model.Response.DataModel;
import com.destinyapp.absensi.Model.ResponseModel;
import com.destinyapp.absensi.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataAbsensiActivity extends AppCompatActivity {
    Spinner tanggal,bulan,tahun;
    RecyclerView recyclerView;
    private List<DataModel> mItems = new ArrayList<>();
    Method method = new Method();
    LinearLayout available;
    int loops = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_absensi);
        tanggal = findViewById(R.id.spTanggal);
        bulan = findViewById(R.id.spBulan);
        tahun = findViewById(R.id.spTahun);
        recyclerView = findViewById(R.id.recycler);
        available = findViewById(R.id.linearAvailable);
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        Date date = new Date();
        tanggal.setSelection(Integer.parseInt(method.MagicDate(day.format(date)))-1);
        bulan.setSelection(Integer.parseInt(method.MagicDate(month.format(date)))-1);

        tanggal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MainLogic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        bulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MainLogic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        tahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MainLogic();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void MainLogic(){
        loops = loops+1;
        if (loops >= 3){
            String Today = tahun.getSelectedItem().toString()+"-"+bulan.getSelectedItem().toString()+"-"+tanggal.getSelectedItem().toString();
            Logic(Today);
        }
    }
    private void Logic(final String getThisDay){
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> Log = api.Karyawan();
        Log.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        available.setVisibility(View.GONE);
                        recyclerView.setLayoutManager(new GridLayoutManager(DataAbsensiActivity.this, 1));
                        mItems=response.body().getData();
                        AdapterDataAbsensi gridAdapter = new AdapterDataAbsensi(DataAbsensiActivity.this,mItems,getThisDay);
                        recyclerView.setAdapter(gridAdapter);
                    }else{
                        available.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    Toast.makeText(DataAbsensiActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(DataAbsensiActivity.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DataAbsensiActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
