package com.destinyapp.absensi.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataModel {
    @SerializedName("id_karyawan")
    @Expose
    public String id_karyawan;

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("password")
    @Expose
    public String password;

    @SerializedName("nama_karyawan")
    @Expose
    public String nama_karyawan;

    @SerializedName("divisi")
    @Expose
    public String divisi;

    @SerializedName("level")
    @Expose
    public String level;

    public String getId_karyawan() {
        return id_karyawan;
    }

    public void setId_karyawan(String id_karyawan) {
        this.id_karyawan = id_karyawan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama_karyawan() {
        return nama_karyawan;
    }

    public void setNama_karyawan(String nama_karyawan) {
        this.nama_karyawan = nama_karyawan;
    }

    public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
