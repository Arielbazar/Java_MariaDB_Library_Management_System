import java.sql.*;
import java.util.*;
import static java.util.Collections.list;
public class UserDAO {
    public static boolean addUser(User user) {
        String sql = "INSERT INTO user (username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            int rows = pstmt.executeUpdate();
            HistoryLogsDAO logDAO = new HistoryLogsDAO(conn);
            logDAO.addLog(AdminFrame.SaveUsername,HistoryLog.AS);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean delUser(String username){
        String sql = "DELETE FROM user WHERE username = ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,username);
            HistoryLogsDAO logDAO = new HistoryLogsDAO(conn);
            logDAO.addLog(AdminFrame.SaveUsername,HistoryLog.DS );
            int rows = pstmt.executeUpdate();
            
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("SET @count = 0");
            stmt.executeUpdate("UPDATE user SET id = (@count := @count + 1) ORDER BY id");
            stmt.executeUpdate("ALTER TABLE user AUTO_INCREMENT = 1");
            return rows > 0;
        
    }catch (SQLException e){
        e.printStackTrace();
        return false;
    }
}
    
    public static boolean updUser(User user) {
        String sql = "UPDATE user SET username=?, password=?, role=? WHERE id=?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
             pstmt.setString(3, user.getRole());
            pstmt.setInt(4, user.getId());
            HistoryLogsDAO logDAO = new HistoryLogsDAO(conn);
            logDAO.addLog(AdminFrame.SaveUsername,HistoryLog.US );
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
  public static List<User> getAllUsers() {
    List<User> users = new ArrayList<>();
    String sql = "SELECT * FROM user";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            User user;
            user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));

            users.add(user);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return users;
}
   public static User getbyId(int id){
        String sql = "SELECT * FROM user WHERE id=?";
        try (Connection conn = DBConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role")
                );
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}

