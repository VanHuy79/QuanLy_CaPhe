/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.BanHang;

import Database.Connect;
import Process.Ban;
import Process.CTHD;
import Process.HoaDon;
import Process.LoaiSanPham;
import Process.SanPham;
import UI.frmBanHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ThanhNam
 */
public class pnlChiTietBan extends javax.swing.JPanel {

    int maban;
    String tenban;
    String trangthai;
    ArrayList<HoaDon> listHD = new ArrayList<>();
    ArrayList<CTHD> listCTHD = new ArrayList<>();
    ArrayList<SanPham> listSP = new ArrayList<>();
    int index;
    Connection conn;
    DefaultTableModel tblmodel;
    DefaultComboBoxModel cboModel;
    
    public static pnlChiTietBan ctb;

    /**
     * Creates new form pnlBan
     */
    public pnlChiTietBan(int MaBan, String TenBan, String TrangThai) {
        initComponents();
        ctb = this;
        
        maban = MaBan;
        tenban = TenBan;
        trangthai = TrangThai;
        lblTenBan.setText(TenBan);
        lblTrangThai.setText(TrangThai);
        btnSua.setEnabled(false);
        btnThem.setEnabled(false);
        btnThanhToan.setEnabled(false);
        txtSoLuong.setText("0");

        conn = Connect.ketnoi("DoAn_QLCafe");
        if (conn != null) {
//            System.out.println("Kết nối thành công.");
            loadToCboLSP();
        } else {
            System.out.println("Lỗi kết nối!!!");
        }
    }

    public void AutoID() {
        // set mã hóa đơn
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i <= 9; i++) {
                    try {
                        int so = (int) Math.round(Math.random() * 100000);
                        lblMaHD.setText("HD" + String.valueOf(so));
                        Thread.sleep(0);
                    } catch (Exception e) {
                        break;
                    }
                }
            }
        }.start();
    }

    public void loadToCboLSP() {
        try {
            String sql = "Select * from LoaiSanPham";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            DefaultComboBoxModel model = (DefaultComboBoxModel) cboLSP.getModel();
            model.removeAllElements();
            while (rs.next()) {
                String malsp = rs.getString("MaLH");
                String tenlsp = rs.getString("TenLH");
                String mota = rs.getString("MoTa");

                model.addElement(new LoaiSanPham(malsp, tenlsp, mota));
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi load Data To comboboxLSP");
        }
    }

    public void loadToCboTenMon(String tenlmh) {
        try {
            LoaiSanPham lsp = (LoaiSanPham) cboLSP.getSelectedItem();
            String sql = "select * from SanPham join LoaiSanPham "
                    + "on SanPham.MaLH = LoaiSanPham.MaLH "
                    + "where TenLH = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setString(1, tenlmh);
            ResultSet rs = st.executeQuery();

            cboModel = (DefaultComboBoxModel) cboTenMon.getModel();
            cboModel.removeAllElements();
            while (rs.next()) {
                String masp = rs.getString("MaSP");
                String malsp = rs.getString("MaLH");
                String tenlsp = rs.getString("TenLH");
                String tensp = rs.getString("TenSP");
                float mota = rs.getFloat("GiaSP");

                cboModel.addElement(new SanPham(masp, tensp, malsp, tenlsp, mota));
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi load data to comboBox TenMon");
        }
    }

    public void addSanPham() {
        try {
            String maHD = lblMaHD.getText();
            String maSP = txtMaMon.getText();
            String tenSP = cboTenMon.getSelectedItem().toString();
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            float giaTien = Float.parseFloat(txtGia.getText());
            float thanhTien = giaTien * soLuong;

            //Thêm Vào List
            listCTHD.add(new CTHD(maHD, maSP, tenSP, soLuong, giaTien, thanhTien));
            cboLSP.setSelectedIndex(0);
            cboTenMon.setSelectedIndex(0);
            txtSoLuong.setText("0");
            lblTongMon.setText(Integer.toString(tblBanHang.getRowCount() + 1));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi Thêm Sản Phẩm");
            e.printStackTrace();
        }
    }

    public void updateSanPham() {
        try {
            index = tblBanHang.getSelectedRow();
            CTHD cthd = listCTHD.get(index);

            String maHD = lblMaHD.getText();
            String maSP = txtMaMon.getText();
            String tenSP = cboTenMon.getSelectedItem().toString();
            int soLuong = Integer.parseInt(txtSoLuong.getText());
            float giaTien = Float.parseFloat(txtGia.getText());
            float thanhTien = giaTien * soLuong;

            cthd.setMaHD(maHD);
            cthd.setMaSP(maSP);
            cthd.setTenSP(tenSP);
            cthd.setSoLuong(soLuong);
            cthd.setGiaTien(giaTien);
            cthd.setThanhTien(thanhTien);

            cboLSP.setSelectedIndex(0);
            cboTenMon.setSelectedIndex(0);
            txtSoLuong.setText("0");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi Sửa Sản Phẩm");
        }
    }

    public void tinhTong() {
        float tong = 0;
        for (int i = 0; i < tblBanHang.getRowCount(); i++) {
            float tt = (float) tblBanHang.getValueAt(i, 3);
            tong += tt;
        }

        lblTongTien.setText(String.valueOf(tong));
    }

    public void fillToTable() {
        tblmodel = (DefaultTableModel) tblBanHang.getModel();
        tblmodel.setRowCount(0);
        for (CTHD cthd : listCTHD) {
            tblmodel.addRow(new Object[]{
                cthd.getTenSP(),
                cthd.getGiaTien(),
                cthd.getSoLuong(),
                cthd.getThanhTien()
            });
        }
    }

//    void insertToCTHD() {
//        try {
//            String maHD = lblMaHD.getText();
//            for (int i = 0; i < listCTHD.size(); i++) {
//                CTHD ct = listCTHD.get(i);
//                String sql = "INSERT INTO CT_HoaDon(MaHD, MaSP, Soluong, TongTien) VALUES(?,?,?,?);";
//                PreparedStatement ps = conn.prepareStatement(sql);
//                ps.setString(1, maHD);
//                ps.setString(2, ct.getMaSP());
//                ps.setInt(3, ct.getSoLuong());
//                ps.setFloat(4, ct.getThanhTien());
//                
//                ps.executeUpdate();
//            }
//
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, "Lỗi thêm CTHD!!!");
//        }
//    }
//
//    void insertToHD() {
//        try {
//            String maHD = lblMaHD.getText();
//            float giaTien = Float.parseFloat(lblTongTien.getText());
//
//            String sql = "INSERT INTO HoaDon(MaHD, MaBan, TongTien) VALUES(?,?,?);";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setString(1, maHD);
//            ps.setInt(2, maban);
//            ps.setFloat(3, giaTien);
//
//            ps.executeUpdate();
//
//        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(this, "Lỗi thêm HD!!!");
//        }
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBanHang = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtMaMon = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboLSP = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        txtGia = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnMoBan = new javax.swing.JButton();
        cboTenMon = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        lblTenBan = new javax.swing.JLabel();
        lblmmm = new javax.swing.JLabel();
        lblTrangThai = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblTongMon = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnThanhToan = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        btnHuy = new javax.swing.JButton();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Thông Tin Chi Tiết", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 24))); // NOI18N

        tblBanHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên Món", "Giá", "Số Lượng", "Thành Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBanHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblBanHangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblBanHang);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Mã Món");

        txtMaMon.setEditable(false);
        txtMaMon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Tên Món");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Loại sản phẩm");

        cboLSP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLSPItemStateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Giá");

        txtGia.setEditable(false);
        txtGia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        btnThem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnMoBan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnMoBan.setText("Mở Bàn");
        btnMoBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoBanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMoBan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnMoBan, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        cboTenMon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboTenMon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboTenMonItemStateChanged(evt);
            }
        });

        jSeparator1.setForeground(new java.awt.Color(0, 204, 204));

        jSeparator2.setForeground(new java.awt.Color(0, 204, 204));

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblTenBan.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTenBan.setText("Tên Bàn");

        lblmmm.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblmmm.setText("Trạng Thái:");

        lblTrangThai.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTrangThai.setText("...");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblmmm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTrangThai, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(lblTenBan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTenBan)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblmmm)
                    .addComponent(lblTrangThai))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator3.setForeground(new java.awt.Color(0, 204, 204));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Số Lượng");

        txtSoLuong.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("Tổng món:");

        lblTongMon.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTongMon.setForeground(new java.awt.Color(51, 51, 255));
        lblTongMon.setText("0");

        lblTongTien.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(51, 51, 255));
        lblTongTien.setText("0");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 255));
        jLabel10.setText("Tổng Tiền:");

        btnThanhToan.setBackground(new java.awt.Color(102, 255, 204));
        btnThanhToan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThanhToan.setText("Thanh Toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setText("Mã HD:");

        lblMaHD.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMaHD.setText("...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblMaHD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(lblMaHD)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnHuy.setBackground(new java.awt.Color(255, 153, 153));
        btnHuy.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnHuy.setText("Hủy");
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(cboLSP, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtGia)
                            .addComponent(txtMaMon)
                            .addComponent(cboTenMon, 0, 245, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSoLuong))
                        .addGap(0, 47, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel10))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTongMon)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(btnThanhToan)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnHuy))
                                    .addComponent(lblTongTien))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator3)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtMaMon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cboTenMon, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtGia, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblTongMon))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTongTien)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboLSPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLSPItemStateChanged
        // TODO add your handling code here:
        if (cboLSP.getSelectedItem() != null) {
            LoaiSanPham lsp = (LoaiSanPham) cboLSP.getSelectedItem();
            loadToCboTenMon(lsp.getTenloaihang());
        }

    }//GEN-LAST:event_cboLSPItemStateChanged

    private void cboTenMonItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboTenMonItemStateChanged
        // TODO add your handling code here:
        try {
            if (cboTenMon.getSelectedItem() != null) {
                SanPham sp = (SanPham) cboTenMon.getSelectedItem();
                txtMaMon.setText(sp.getMaSP());
                txtGia.setText(Float.toString(sp.getGiaSP()));
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_cboTenMonItemStateChanged

    void updateTrangThai(String TrangThai) {
        try {
            String sql = "UPDATE Ban SET TenBan = ?, TrangThai = ? where MaBan = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tenban);
            ps.setString(2, trangthai);
            ps.setInt(3, maban);

            int row = ps.executeUpdate();
            if (row > 0) {
                frmBanHang.bh.FillBan();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi sửa!!!");
        }
    }

    private void btnMoBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoBanActionPerformed
        // TODO add your handling code here:
        listCTHD.removeAll(listCTHD);
        AutoID();

        btnThem.setEnabled(true);
        btnSua.setEnabled(true);
        btnThanhToan.setEnabled(true);
        btnMoBan.setEnabled(false);
        trangthai = "Đang Có Khách";
        lblTrangThai.setText(trangthai);
        updateTrangThai(trangthai);
    }//GEN-LAST:event_btnMoBanActionPerformed

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        // TODO add your handling code here:
        listCTHD.removeAll(listCTHD);
        btnThem.setEnabled(false);
        btnSua.setEnabled(false);
        btnThanhToan.setEnabled(false);
        btnMoBan.setEnabled(true);
        trangthai = "Trống";
        lblMaHD.setText("...");
        lblTrangThai.setText(trangthai);
        updateTrangThai(trangthai);
        tblmodel.setRowCount(0);
        lblTongMon.setText("0");
        lblTongTien.setText("0");
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        addSanPham();
        fillToTable();
        tinhTong();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        updateSanPham();
        fillToTable();
        tinhTong();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void tblBanHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBanHangMouseClicked
        // TODO add your handling code here:
        index = tblBanHang.getSelectedRow();
        if (index >= 0) {
            CTHD hd = listCTHD.get(index);

            txtMaMon.setText(hd.getMaSP());
            cboModel.setSelectedItem(hd.getTenSP());
            txtSoLuong.setText(Integer.toString(hd.getSoLuong()));
            txtGia.setText(Float.toString(hd.getGiaTien()));

            tblBanHang.setRowSelectionInterval(index, index);
        }
    }//GEN-LAST:event_tblBanHangMouseClicked

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        try {
            new frmThanhToan().setVisible(true);
//            listCTHD.removeAll(listCTHD);
 
        } catch (Exception e) {
        }
//        btnThem.setEnabled(false);
//        btnSua.setEnabled(false);
//        btnThanhToan.setEnabled(false);
//        btnMoBan.setEnabled(true);
//        trangthai = "Trống";
//        lblMaHD.setText("...");
//        lblTrangThai.setText(trangthai);
//        updateTrangThai(trangthai);
//        tblmodel.setRowCount(0);
//        lblTongMon.setText("0");
//        lblTongTien.setText("0");
    }//GEN-LAST:event_btnThanhToanActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    public javax.swing.JButton btnMoBan;
    public javax.swing.JButton btnSua;
    public javax.swing.JButton btnThanhToan;
    public javax.swing.JButton btnThem;
    private javax.swing.JComboBox<String> cboLSP;
    private javax.swing.JComboBox<String> cboTenMon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    public javax.swing.JLabel lblMaHD;
    private javax.swing.JLabel lblTenBan;
    public javax.swing.JLabel lblTongMon;
    public javax.swing.JLabel lblTongTien;
    public javax.swing.JLabel lblTrangThai;
    private javax.swing.JLabel lblmmm;
    private javax.swing.JTable tblBanHang;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaMon;
    private javax.swing.JTextField txtSoLuong;
    // End of variables declaration//GEN-END:variables
}
