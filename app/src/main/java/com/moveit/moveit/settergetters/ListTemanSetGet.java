package com.moveit.moveit.settergetters;

import java.io.Serializable;

/**
 * Created by Arie Ahmad on 10/18/2017.
 */

public class ListTemanSetGet implements Serializable {
    String email, jam, lokasi, nama, status, tgl, lat, longi, olahraga;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTgl() {
        return tgl;
    }

    public void setTgl(String tgl) {
        this.tgl = tgl;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getOlahraga() {
        return olahraga;
    }

    public void setOlahraga(String olahraga) {
        this.olahraga = olahraga;
    }
}
