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
public class SanPham {

    private String MaSP;
    private String TenSP;
    private String MaLH;
    private String TenLH;
    private float GiaSP;

    public SanPham(String MaSP, String TenSP, String MaLH, String TenLH, float GiaSP) {
        this.MaSP = MaSP;
        this.TenSP = TenSP;
        this.MaLH = MaLH;
        this.TenLH = TenLH;
        this.GiaSP = GiaSP;
    }    

    public SanPham() {
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

    public String getMaLH() {
        return MaLH;
    }

    public void setMaLH(String MaLH) {
        this.MaLH = MaLH;
    }

    public String getTenLH() {
        return TenLH;
    }

    public void setTenLH(String TenLH) {
        this.TenLH = TenLH;
    }

    public float getGiaSP() {
        return GiaSP;
    }

    public void setGiaSP(float GiaSP) {
        this.GiaSP = GiaSP;
    }

    @Override
    public String toString() {
        return TenSP;
    }
}
