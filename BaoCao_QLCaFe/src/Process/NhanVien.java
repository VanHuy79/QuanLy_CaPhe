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
public class NhanVien {

    private String MaNV;
    private String TenNV;
    private String GioiTinh;
    private String DiaChi;
    private int SDT;
    private String TaiKhoan;
    private String MatKhau;
    private String ChucVu;

    public NhanVien(String MaNV, String TenNV, String GioiTinh, String DiaChi, int SDT, String TaiKhoan, String MatKhau, String ChucVu) {
        this.MaNV = MaNV;
        this.TenNV = TenNV;
        this.GioiTinh = GioiTinh;
        this.DiaChi = DiaChi;
        this.SDT = SDT;
        this.TaiKhoan = TaiKhoan;
        this.MatKhau = MatKhau;
        this.ChucVu = ChucVu;
    }
    
    public NhanVien(String TaiKhoan, String MatKhau, String ChucVu) {
        this.TaiKhoan = TaiKhoan;
        this.MatKhau = MatKhau;
        this.ChucVu = ChucVu;
    }

    public NhanVien() {
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getTenNV() {
        return TenNV;
    }

    public void setTenNV(String TenNV) {
        this.TenNV = TenNV;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public int getSDT() {
        return SDT;
    }

    public void setSDT(int SDT) {
        this.SDT = SDT;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String TaiKhoan) {
        this.TaiKhoan = TaiKhoan;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String ChucVu) {
        this.ChucVu = ChucVu;
    }

    @Override
    public String toString() {
        return "NhanVien{" + "MaNV=" + MaNV + ", TenNV=" + TenNV + ", GioiTinh=" + GioiTinh + ", " + "DiaChi=" + DiaChi + ", "
                + "SDT=" + SDT + ", TaiKhoan=" + TaiKhoan + ", MatKhau = " + MatKhau + ", ChucVu =" + ChucVu + '}';
    }
}
