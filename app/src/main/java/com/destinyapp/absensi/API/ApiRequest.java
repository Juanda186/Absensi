package com.destinyapp.absensi.API;

import com.destinyapp.absensi.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequest {
    @FormUrlEncoded
    @POST("Login")
    Call<ResponseModel> login(@Field("username") String username,
                              @Field("password") String password);

    @GET("Karyawan")
    Call<ResponseModel> Karyawan();

    @FormUrlEncoded
    @POST("Checkabsen")
    Call<ResponseModel> CheckAbsen(@Field("id_karyawan") String id_karyawan,
                              @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("Insertabsen")
    Call<ResponseModel> InsertAbsen(@Field("id_karyawan") String id_karyawan,
                                   @Field("status") String status,
                                   @Field("tanggal") String tanggal);

    @FormUrlEncoded
    @POST("Editabsen")
    Call<ResponseModel> UpdateAbsen(@Field("id_absensi") String id_absensi,
                                   @Field("status") String status);

    @FormUrlEncoded
    @POST("Insertkaryawan")
    Call<ResponseModel> InsertKaryawan(@Field("username") String username,
                                       @Field("password") String password,
                                       @Field("nama_karyawan") String nama_karyawan,
                                       @Field("divisi") String divisi);


    @FormUrlEncoded
    @POST("Checkemail")
    Call<ResponseModel> CheckEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("Register")
    Call<ResponseModel> register(@Field("email") String email,
                                 @Field("password") String password,
                                 @Field("nama") String nama,
                                 @Field("alamat") String alamat,
                                 @Field("telpon") String telpon,
                                 @Field("nik") String nik);
}
