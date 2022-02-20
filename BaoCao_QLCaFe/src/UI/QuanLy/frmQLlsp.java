/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.QuanLy;

import Database.Connect;
import Process.Ban;
import Process.LoaiSanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author ThanhNam
 */
public class frmQLlsp extends javax.swing.JInternalFrame {

    /**
     * Creates new form frmQLlsp
     */
    
    Connection conn;
    List<LoaiSanPham> list = new ArrayList<>();
    int index;
    
    public frmQLlsp() {
        initComponents();
        conn = Connect.ketnoi("DoAn_QLCafe");
        if (conn != null) {
//            System.out.println("Kết nối thành công.");
            loadDataToList();
            fillToTable();
        } else {
            System.out.println("Lỗi kết nối!!!");
        }
    }
    
    private void loadDataToList() {
        try {
            String sql = "select * from LoaiSanPham";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            //duyệt rs => đổ dữ liệu vào list
            while (rs.next()) {
                String malsp = rs.getString("MaLH");
                String tenlsp = rs.getString("TenLH");
                String mota = rs.getString("MoTa");
                list.add(new LoaiSanPham(malsp, tenlsp, mota));
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi load data to list");
        }
    }

    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tblLoaiSanPham.getModel();
        model.setRowCount(0);
        for (LoaiSanPham lsp : list) {
            model.addRow(new Object[]{
                lsp.getMaloaihang(),
                lsp.getTenloaihang(),
                lsp.getMota()
            });
        }
    }

    public void ClearForm() {
        this.txtMaLoaiHang.setText("");
        this.txtTenLoaiHang.setText("");
        this.txtMoTa.setText("");
        this.txtMaLoaiHang.requestFocus();
        tblLoaiSanPham.setSelectionMode(0);
    }

    public void Them() {
        try {
            String malsp = txtMaLoaiHang.getText();
            String tenlsp = txtTenLoaiHang.getText();
            String mota = txtMoTa.getText();
            list.add(new LoaiSanPham(malsp, tenlsp, mota));

            String sql = "INSERT INTO LoaiSanPham VALUES(?,?,?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, malsp);
            ps.setString(2, tenlsp);
            ps.setString(3, mota);

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
            index = tblLoaiSanPham.getSelectedRow();
            String malsp = txtMaLoaiHang.getText();
            String tenlsp = txtTenLoaiHang.getText();
            String mota = txtMoTa.getText();
            list.set(index, new LoaiSanPham(malsp, tenlsp, mota));

            String sql = "UPDATE LoaiSanPham SET TenLH = ?, MoTa = ? where MaLH = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, tenlsp);
            ps.setString(2, mota);
            ps.setString(3, malsp);

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
            index = tblLoaiSanPham.getSelectedRow();
            list.remove(index);
            //xóa trong CSDL
            String sql = "delete from LoaiSanPham where MaLH = ?";
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setString(1, txtMaLoaiHang.getText());

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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblLoaiSanPham = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMaLoaiHang = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTenLoaiHang = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtMoTa = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnClear = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();

        tblLoaiSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã Loại Hàng", "Tên Loại Hàng", "Mô Tả"
            }
        ));
        tblLoaiSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLoaiSanPham);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Loại Sản Phẩm");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Mã Loại Hàng");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Tên Loại Hàng");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Mô Tả");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        jScrollPane2.setViewportView(txtMoTa);

        btnThem.setBackground(new java.awt.Color(0, 102, 102));
        btnThem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnClear.setBackground(new java.awt.Color(0, 102, 102));
        btnClear.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnClear.setText("Load");
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(0, 102, 102));
        btnSua.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(0, 102, 102));
        btnXoa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnClear, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(170, 170, 170)
                                .addComponent(jLabel1))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtMaLoaiHang, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                    .addComponent(txtTenLoaiHang, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 9, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtMaLoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtTenLoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblLoaiSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiSanPhamMouseClicked
        // TODO add your handling code here:
        int ind = tblLoaiSanPham.getSelectedRow();
        if (ind >= 0) {
            index = ind;
            LoaiSanPham lsp = list.get(ind);
            txtMaLoaiHang.setText(lsp.getMaloaihang());
            txtTenLoaiHang.setText(lsp.getTenloaihang());
            txtMoTa.setText(lsp.getMota());
        }
    }//GEN-LAST:event_tblLoaiSanPhamMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        Them();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        Sua();
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        Xoa();
        fillToTable();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        // TODO add your handling code here:
        ClearForm();
    }//GEN-LAST:event_btnClearActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblLoaiSanPham;
    private javax.swing.JTextField txtMaLoaiHang;
    private javax.swing.JTextArea txtMoTa;
    private javax.swing.JTextField txtTenLoaiHang;
    // End of variables declaration//GEN-END:variables
}
