/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
public class Userlogin {
    
    public static User login(String username, String password){
        String sql = "SELECT * FROM user WHERE username=? AND password=?";
        try (Connection conn = DBConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                int id = rs.getInt("id");
                String role = rs.getString("role");
                
                HistoryLogsDAO logDAO = new HistoryLogsDAO(conn);
                logDAO.addLog(username,HistoryLog.LOGIN);
                Session.CurrentUsername = username;
                Session.CurrentPassword = password;
                return new User(id, username,password, role);
            }else {
                return null;
            }
            
            
        } catch(SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    
}
