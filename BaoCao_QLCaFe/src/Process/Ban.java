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
public class Ban {
    private int Maban;
    private String Tenban;
    private String Trangthai;

    public Ban(int Maban, String Tenban, String Trangthai) {
        this.Maban = Maban;
        this.Tenban = Tenban;
        this.Trangthai = Trangthai;
    }

    public Ban() {
    }

    public int getMaban() {
        return Maban;
    }

    public void setMaban(int Maban) {
        this.Maban = Maban;
    }

    public String getTenban() {
        return Tenban;
    }

    public void setTenban(String Tenban) {
        this.Tenban = Tenban;
    }

    public String getTrangthai() {
        return Trangthai;
    }

    public void setTrangthai(String Trangthai) {
        this.Trangthai = Trangthai;
    }

    @Override
    public String toString() {
        return "Phong{" + "Maph=" + Maban + ", Tenph=" + Tenban + ", Trangthai=" + Trangthai + "}";
    }
}
