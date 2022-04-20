/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package th.javanangcao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DOTNETGROUP
 */
public class KetNoiSQL {

    private Connection con = null;

    public KetNoiSQL() {
        String url = "net.sourceforge.jtds.jdbc.Driver";
        try {
            Class.forName(url);
            String dbUrl = "jdbc:jtds:sqlserver://LAPTOP-RHVCMDE6:1433/Account";
            con = DriverManager.getConnection(dbUrl);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(KetNoiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param NoAccount
     * @throws java.sql.SQLException
     */
    public void traCuuTaiKhoan(int STT) throws SQLException {
        String sql = "Select STT from Account where STT=" + STT;
        PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        if (rs.next() == true) {
            System.out.println("Tìm thấy tài khoản");
        } else {
            System.out.println("Tìm kh thấy tài khoản");

        }
    }

    public ResultSet GetResultSet(String tableName) throws SQLException {
        ResultSet rs = null;
        Statement stmt = con.createStatement();
        String sql = "select * from " + tableName;
        rs = stmt.executeQuery(sql);
        return rs;
    }

    public void infoAll() throws SQLException {
        NumberFormat df = new DecimalFormat("#,###");
        ResultSet rs = GetResultSet("Account");
        System.out.println("THÔNG TIN TÀI KHOẢN NGÂNG HÀNG");
        while (rs.next()) {
            System.out.print(rs.getString("STT"));
            System.out.print(" - ");
            System.out.print(rs.getString("Name"));
            System.out.print(" - ");
            System.out.println(df.format(Double.parseDouble(rs.getString("Money"))) + " VNĐ ");
        }

    }

    public void createAccount() throws SQLException {
        List<NguoiDung> listuser = new ArrayList<NguoiDung>();
        int n = ThuVien.getInt("Nhập số lượng người thêm vào hệ thống");
        for (int i = 0; i < n; i++) {
            listuser.add(new NguoiDung(ThuVien.getInt("Số tài khoản: "), ThuVien.getString("Họ tên"), ThuVien.getDouble("Số tiền")));

        }

        String sql = "INSERT INTO Account (STT, Name, Money) VALUES (?, ?, ?);";
        PreparedStatement psmt = con.prepareStatement(sql);
        for (NguoiDung user : listuser) {
            psmt.setInt(1, user.getSTT());
            psmt.setString(2, user.getName());
            psmt.setDouble(3, user.getMoney());
            psmt.execute();
            
        } 
    }
}


       