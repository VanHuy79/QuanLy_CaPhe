/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.QuanLy;

import Database.Connect;
import Process.LoaiSanPham;
import Process.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author ThanhNam
 */
public class frmQLSanPham extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmQLSanPham
     */
    ArrayList<SanPham> list = new ArrayList<>();
    int index;
    DefaultTableModel tblModel;
    DefaultComboBoxModel cboModel;
    Connection conn;

    public frmQLSanPham() {
        initComponents();

        tblModel = (DefaultTableModel) tblSanPham.getModel();
        cboModel = (DefaultComboBoxModel) cboLSP.getModel();
        conn = Connect.ketnoi("DoAn_QLCafe");
        if (conn != null) {
//            System.out.println("Kết nối thành công.");
            loadDataToList();
            loadDataToCombobox();
            fillToTable();
        } else {
            System.out.println("Lỗi kết nối!!!");
        }
    }

    private void loadDataToList() {
        try {
            String sql = "select * from SanPham join LoaiSanPham on SanPham.MaLH = LoaiSanPham.MaLH";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            //duyệt rs => đổ dữ liệu vào list
            while (rs.next()) {
                String masp = rs.getString("MaSP");
                String malsp = rs.getString("MaLH");
                String tenlsp = rs.getString("TenLH");
                String tensp = rs.getString("TenSP");
                float mota = rs.getFloat("GiaSP");
                list.add(new SanPham(masp, tensp, malsp, tenlsp, mota));
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi load data to list");
        }
    }

    public void loadDataToCombobox() {
        try {
            String sql = "Select * from LoaiSanPham";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            cboModel.removeAllElements();
            while (rs.next()) {
                String malsp = rs.getString("MaLH");
                String tenlsp = rs.getString("TenLH");
                String mota = rs.getString("MoTa");
                
                cboModel.addElement(new LoaiSanPham(malsp, tenlsp, mota));
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi load Data To combobox");
        }
    }

    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblSanPham.getModel();
        model.setRowCount(0);
        for (SanPham sp : list) {
            model.addRow(new Object[]{
                sp.getMaSP(),
                sp.getTenSP(),
                sp.getMaLH(),
                sp.getTenLH(),
                sp.getGiaSP()
            });
        }
    }

    public void ClearForm() {
        this.txtMaSP.setText("");
        this.txtTenSP.setText("");
        this.cboLSP.setSelectedIndex(0);
        this.txtGiaSP.setText("");
        this.txtMaSP.requestFocus();
        tblSanPham.setSelectionMode(0);
    }

    public void Them() {
        try {
            String masp = txtMaSP.getText();
            String tensp = txtTenSP.getText();
            String malsp = txtMaLSP.getText();
            String tenlsp = String.valueOf(cboLSP.getSelectedItem());
            float giasp = Float.parseFloat(txtGiaSP.getText());
            list.add(new SanPham(masp, tensp, malsp, tenlsp, giasp));

            String sql = "INSERT INTO SanPham VALUES(?,?,?,?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, masp);
            ps.setString(2, malsp);
            ps.setString(3, tensp);
            ps.setFloat(4, giasp);

            int row = ps.executeUpdate();
            if (row > 0) {
                JOptionPane.showMessageDialog(this, "Thêm thành công.");
                fillToTable();
                ClearForm();
                index = list.size() - 1;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi thêm!!!");
        }
    }

    public void Sua() {
        try {
            index = tblSanPham.getSelectedRow();
            String masp = txtMaSP.getText();
            String tensp = txtTenSP.getText();
            String malsp = txtMaLSP.getText();
            String tenlsp = String.valueOf(cboLSP.getSelectedItem());
            float giasp = Float.parseFloat(txtGiaSP.getText());
            list.set(index, new SanPham(masp, tensp, malsp, tenlsp, giasp));

            String sql = "UPDATE SanPham SET MaLH = ?, TenSP = ?, GiaSP = ? where MaSP = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, malsp);
            ps.setString(2, tensp);
            ps.setFloat(3, giasp);
            ps.setString(4, masp);

            int row = ps.executeUpdate();
            if (row > 0) {
                JOptionPane.showMessageDialog(this, "Sửa thành công.");
                fillToTable();
                ClearForm();
                index = list.size() - 1;
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi sửa!!!");
        }
    }

    public void Xoa() {
        try {
            if (list.size() <= 0) {
                JOptionPane.showMessageDialog(this, "Không còn dữ liệu để xóa ");
                return;
            }
            int hoi = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa?", "HỎi xóa", JOptionPane.YES_NO_OPTION);
            if (hoi != JOptionPane.YES_OPTION) {
                return;
            }
            //xóa trong list
            index = tblSanPham.getSelectedRow();
            list.remove(index);
            //xóa trong CSDL
            String sql = "delete from SanPham where MaSP = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, txtMaSP.getText());

            int row = pstm.executeUpdate();
            if (row > 0) {
                JOptionPane.showMessageDialog(this, "xóa thành công ");
                ClearForm();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi xóa sản phẩm");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaSP = new javax.swing.JTextField();
        txtTenSP = new javax.swing.JTextField();
        txtGiaSP = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnNhapLai = new javax.swing.JButton();
        cboLSP = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtMaLSP = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Quản Lý Sản Phẩm");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Mã Sản Phẩm:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setText("Tên Sản Phẩm:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setText("Loại Sản Phẩm:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("Giá Sản Phẩm");

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Mã LSP", "Tên LSP", "Gía sản phẩm"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        btnThem.setBackground(new java.awt.Color(51, 255, 255));
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 255, 255));
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnNhapLai.setBackground(new java.awt.Color(0, 255, 255));
        btnNhapLai.setText("Load");
        btnNhapLai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapLaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnNhapLai, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNhapLai, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cboLSP.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboLSPItemStateChanged(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel6.setText("Mã LSP:");

        txtMaLSP.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGap(47, 47, 47)
                                            .addComponent(jLabel2))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtTenSP, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGiaSP, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtMaSP)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(cboLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtMaLSP))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(241, 241, 241)
                                .addComponent(jLabel1)))
                        .addGap(0, 177, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTenSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtMaLSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtGiaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
        int ind = tblSanPham.getSelectedRow();
        if (ind >= 0) {
            index = ind;
            SanPham sp = list.get(ind);            
            txtMaSP.setText(sp.getMaSP());
            txtTenSP.setText(sp.getTenSP());
            txtMaLSP.setText(sp.getMaLH());
            cboModel.setSelectedItem(sp.getTenLH());
            txtGiaSP.setText(Double.toString(sp.getGiaSP()));
        }
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void cboLSPItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboLSPItemStateChanged
        // TODO add your handling code here:
        try {
            LoaiSanPham lsp = (LoaiSanPham) cboLSP.getSelectedItem();
            txtMaLSP.setText(lsp.getMaloaihang());
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cboLSPItemStateChanged

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        Them();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        Xoa();
        fillToTable();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        Sua();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnNhapLaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapLaiActionPerformed
        // TODO add your handling code here:
        ClearForm();
    }//GEN-LAST:event_btnNhapLaiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNhapLai;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboLSP;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblSanPham;
    private javax.swing.JTextField txtGiaSP;
    private javax.swing.JTextField txtMaLSP;
    private javax.swing.JTextField txtMaSP;
    private javax.swing.JTextField txtTenSP;
    // End of variables declaration//GEN-END:variables
}
