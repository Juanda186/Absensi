package com.destinyapp.absensi.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.destinyapp.absensi.API.ApiRequest;
import com.destinyapp.absensi.API.RetroServer;
import com.destinyapp.absensi.Model.ResponseModel;
import com.destinyapp.absensi.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahKaryawan extends AppCompatActivity {
    EditText username,password,nama;
    Spinner divisi;
    LinearLayout tambah;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_karyawan);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        nama = findViewById(R.id.etNama);
        divisi = findViewById(R.id.spinnerDivisi);
        tambah = findViewById(R.id.btnTambah);

        tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Chekers().equals("DONE")){
                    Toast.makeText(TambahKaryawan.this, Chekers(), Toast.LENGTH_SHORT).show();
                }else{
                    Logic();
                }
            }
        });
    }
    private String Chekers(){
        String check = "DONE";
        if (username.getText().toString().isEmpty()){
            check = "Username tidak boleh kosong";
        }else if(password.getText().toString().isEmpty()){
            check = "Password Tidak boleh kosong";
        }else if(nama.getText().toString().isEmpty()){
            check = "Nama tidak boleh kosong";
        }
        return check;
    }
    private void Logic(){
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Sedang Mencoba Menambahkan Data Karyawan");
        pd.setCancelable(false);
        pd.show();

        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);
        Call<ResponseModel> log = api.InsertKaryawan(
                username.getText().toString(),
                password.getText().toString(),
                nama.getText().toString(),
                divisi.getSelectedItem().toString());
        log.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                try {
                    if (response.body().getStatus().equals("success")){
                        Intent intent = new Intent(TambahKaryawan.this,TambahActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(TambahKaryawan.this, "Pembuatan karyawan Gagal", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(TambahKaryawan.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TambahKaryawan.this, "Koneksi Gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}