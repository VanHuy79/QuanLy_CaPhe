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
public class LoaiSanPham {

    private String Maloaihang;
    private String Tenloaihang;
    private String Mota;

    public LoaiSanPham(String Maloaihang, String Tenloaihang, String Mota) {
        this.Maloaihang = Maloaihang;
        this.Tenloaihang = Tenloaihang;
        this.Mota = Mota;
    }

    public LoaiSanPham() {
    }

    public String getMaloaihang() {
        return Maloaihang;
    }

    public void setMaloaihang(String Maloaihang) {
        this.Maloaihang = Maloaihang;
    }

    public String getTenloaihang() {
        return Tenloaihang;
    }

    public void setTenloaihang(String Tenloaihang) {
        this.Tenloaihang = Tenloaihang;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String Mota) {
        this.Mota = Mota;
    }

    @Override
    public String toString() {
        return Tenloaihang;
    }
}
