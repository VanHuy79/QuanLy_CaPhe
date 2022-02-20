/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Process;

/**
 *
 * @author ThanhNam
 */
public class tkBanChay {
    String tensp;
    float giasp;
    int daban;

    public tkBanChay(String tensp, float giasp, int daban) {
        this.tensp = tensp;
        this.giasp = giasp;
        this.daban = daban;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public float getGiasp() {
        return giasp;
    }

    public void setGiasp(float giasp) {
        this.giasp = giasp;
    }

    public int getDaban() {
        return daban;
    }

    public void setDaban(int daban) {
        this.daban = daban;
    }
    
    
}
