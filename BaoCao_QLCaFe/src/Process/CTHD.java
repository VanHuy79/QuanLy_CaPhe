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
public class CTHD {
    String MaHD;
    String MaSP;
    String TenSP;
    int soLuong;
    float giaTien;
    float thanhTien;
    Date ngayTao;

    public CTHD() {
    }

    public CTHD(String MaHD, String MaSP, String TenSP, int SoLuong, float GiaTien, float ThanhTien) {
        this.MaHD = MaHD;
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.soLuong = SoLuong;
        this.giaTien = GiaTien;
        this.thanhTien = ThanhTien;
    }

    public CTHD(String TenSP, float giaTien) {
        this.TenSP = TenSP;
        this.giaTien = giaTien;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String TenSP) {
        this.TenSP = TenSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public float getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(float giaTien) {
        this.giaTien = giaTien;
    }

    public float getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(float ThanhTien) {
        this.thanhTien = ThanhTien;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    
}
