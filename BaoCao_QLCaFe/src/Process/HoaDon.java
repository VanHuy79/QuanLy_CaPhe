/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

import java.util.Date;

/**
 *
 * @author ThanhNam
 */
public class HoaDon {
    String MaHD;
    String MaNV;
    int MaBan;
    int TongMon;
    float TongTien;
    Date NgayXuat;

    public HoaDon() {
    }

    public HoaDon(String MaHD, int MaBan, float TongTien) {
        this.MaHD = MaHD;
        this.MaBan = MaBan;
        this.TongTien = TongTien;
    }

    public HoaDon(String MaHD, String MaNV, int MaBan, int TongMon, float TongTien, Date NgayXuat) {
        this.MaHD = MaHD;
        this.MaNV = MaNV;
        this.MaBan = MaBan;
        this.TongMon = TongMon;
        this.TongTien = TongTien;
        this.NgayXuat = NgayXuat;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public int getMaBan() {
        return MaBan;
    }

    public void setMaBan(int MaBan) {
        this.MaBan = MaBan;
    }

    public int getTongMon() {
        return TongMon;
    }

    public void setTongMon(int TongMon) {
        this.TongMon = TongMon;
    }

    public float getTongTien() {
        return TongTien;
    }

    public void setTongTien(float TongTien) {
        this.TongTien = TongTien;
    }

    public Date getNgayXuat() {
        return NgayXuat;
    }

    public void setNgayXuat(Date NgayXuat) {
        this.NgayXuat = NgayXuat;
    }
    
    
    
}
