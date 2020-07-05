package com.destinyapp.absensi.Model;

import com.destinyapp.absensi.Model.Response.DataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    @SerializedName("status")
    @Expose
    String status;

    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("data")
    @Expose
    List<DataModel> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataModel> getData() {
        return data;
    }

    public void setData(List<DataModel> data) {
        this.data = data;
    }
}
