package com.destinyapp.absensi.Activity.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.destinyapp.absensi.Activity.AbsenActivity;
import com.destinyapp.absensi.Activity.AbsensiActivity;
import com.destinyapp.absensi.Activity.DataAbsensiActivity;
import com.destinyapp.absensi.Activity.DataKaryawanActivity;
import com.destinyapp.absensi.Activity.TambahKaryawan;
import com.destinyapp.absensi.Model.Method;
import com.destinyapp.absensi.R;
import com.destinyapp.absensi.SharedPreferance.DB_Helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    ImageView ivHeader;
    TextView tvHeader,tvTgl;
    DB_Helper dbHelper;
    String username,nama,divisi,level;
    LinearLayout Karyawan,DataAbsensi,Absensi,Tambah;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbHelper = new DB_Helper(getActivity());
        Cursor cursor = dbHelper.checkSession();
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                username = cursor.getString(0);
                nama = cursor.getString(1);
                divisi = cursor.getString(2);
                level = cursor.getString(3);
            }
        }
        tvTgl=view.findViewById(R.id.tvTgl);
        ivHeader=view.findViewById(R.id.ivHeader);
        tvHeader=view.findViewById(R.id.tvHeader);
        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        if (nama!=null){
            if (hour > 4 && hour < 11){
                tvHeader.setText("Selamat Pagi, "+nama);
                ivHeader.setImageResource(R.drawable.morning);
            }else if(hour >= 11 && hour <15){
                tvHeader.setText("Selamat Siang, "+nama);
                ivHeader.setImageResource(R.drawable.afternoon);
            }else if(hour >= 15 && hour <18){
                tvHeader.setText("Selamat Sore, "+nama);
                ivHeader.setImageResource(R.drawable.evening);
            }else{
                tvHeader.setText("Selamat Malam, "+nama);
                ivHeader.setImageResource(R.drawable.night);
            }
        }else{
            if (hour > 4 && hour < 11){
                tvHeader.setText("Selamat Pagi");
                ivHeader.setImageResource(R.drawable.morning);
            }else if(hour >= 11 && hour <15){
                tvHeader.setText("Selamat Siang");
                ivHeader.setImageResource(R.drawable.afternoon);
            }else if(hour >= 15 && hour <18){
                tvHeader.setText("Selamat Sore");
                ivHeader.setImageResource(R.drawable.evening);
            }else{
                tvHeader.setText("Selamat Malam");
                ivHeader.setImageResource(R.drawable.night);
            }
        }
        if (level!="1"){

        }

        Absensi = view.findViewById(R.id.linearAbsensi);
        DataAbsensi = view.findViewById(R.id.linearDataAbsensi);
        Karyawan = view.findViewById(R.id.linearKaryawan);
        Tambah = view.findViewById(R.id.linearTambah);
        Method method = new Method();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String thisDay = dateFormat.format(date);
        tvTgl.setText(method.getToday()+", "+thisDay);

        Absensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AbsensiActivity.class);
                startActivity(intent);
            }
        });
        DataAbsensi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataAbsensiActivity.class);
                startActivity(intent);
            }
        });
        Karyawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DataKaryawanActivity.class);
                startActivity(intent);
            }
        });
        Tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TambahKaryawan.class);
                startActivity(intent);
            }
        });
    }
}
