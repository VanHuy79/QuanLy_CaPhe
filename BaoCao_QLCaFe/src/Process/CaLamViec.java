/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

/**
 *
 * @author admin
 */
public class CaLamViec {

    private String MaCaLamViec;
    private String TenCaLamViec;
    private float SoTien;

    public CaLamViec() {
    }

    public CaLamViec(String MaCaLamViec, String TenCaLamViec, float SoTien) {
        this.MaCaLamViec = MaCaLamViec;
        this.TenCaLamViec = TenCaLamViec;
        this.SoTien = SoTien;
    }

    public String getMaCaLamViec() {
        return MaCaLamViec;
    }

    public void setMaCaLamViec(String MaCaLamViec) {
        this.MaCaLamViec = MaCaLamViec;
    }

    public String getTenCaLamViec() {
        return TenCaLamViec;
    }

    public void setTenCaLamViec(String TenCaLamViec) {
        this.TenCaLamViec = TenCaLamViec;
    }

    public float getSoTien() {
        return SoTien;
    }

    public void setSoTien(float SoTien) {
        this.SoTien = SoTien;
    }

    @Override
    public String toString() {
        return "CaLamViec{" + "MaCaLamViec=" + MaCaLamViec + ", TenCaLamViec=" + TenCaLamViec + ",SoTien= " + SoTien + "}";
    }
}
