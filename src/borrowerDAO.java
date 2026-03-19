
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class borrowerDAO {
    
    
    public static boolean addBorrower(Borrower borrower) {
        String sql = "INSERT INTO borrower (name, email, Address, ContactN) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, borrower.getName());
            pstmt.setString(2, borrower.getEmail());
            pstmt.setString(3, borrower.getAddress());
            pstmt.setString(4, borrower.getContactN());
            int rows = pstmt.executeUpdate();
            HistoryLogsDAO logDAO = new HistoryLogsDAO(conn);
            logDAO.addLog(Staff_Frame.SaveUsername,HistoryLog.ABR);
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean delBorrower(String name){
        String sql = "DELETE FROM borrower WHERE name = ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,name);
            HistoryLogsDAO logDAO = new HistoryLogsDAO(conn);
            logDAO.addLog(Staff_Frame.SaveUsername,HistoryLog.DBR);
            int rows = pstmt.executeUpdate();
            
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("SET @count = 0");
            stmt.executeUpdate("UPDATE borrower SET id = (@count := @count + 1) ORDER BY id");
            stmt.executeUpdate("ALTER TABLE borrower AUTO_INCREMENT = 1");
            return rows > 0;
        
    }catch (SQLException e){
        e.printStackTrace();
        return false;
    }
}
    
    public static boolean updBorrower(Borrower borrower) {
        String sql = "UPDATE borrower SET name=?, email=?, Address=?, ContactN=? WHERE id=?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, borrower.getName());
            pstmt.setString(2, borrower.getEmail());
             pstmt.setString(3, borrower.getAddress());
             pstmt.setString(4, borrower.getContactN());
            pstmt.setInt(5, borrower.getId());
            HistoryLogsDAO logDAO = new HistoryLogsDAO(conn);
            logDAO.addLog(Staff_Frame.SaveUsername,HistoryLog.UBR);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
  public static List<Borrower> getAllBorrowers() {
    List<Borrower> borrowers = new ArrayList<>();
    String sql = "SELECT * FROM borrower"; 

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            Borrower borrower = new Borrower();

            borrower.setId(rs.getInt("id"));
            borrower.setName(rs.getString("name"));         
            borrower.setEmail(rs.getString("email"));        
            borrower.setAddress(rs.getString("Address"));    
            borrower.setContactN(rs.getString("ContactN")); 

            borrowers.add(borrower);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return borrowers;
}
   public static Borrower getbyId(int id){
        String sql = "SELECT * FROM borrower WHERE id=?";
        try (Connection conn = DBConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Borrower(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("Address"),
                 rs.getString("ContactN")
                );
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
   
    public static List<Borrower> SearchBorrower(String keywords) {
    List<Borrower> borrowers = new ArrayList<>();
    String sql = "SELECT * FROM borrower WHERE name LIKE ? OR address LIKE ? OR email LIKE ?";

    try {
        Connection conn = DBConnect.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, "%" + keywords + "%");
        pst.setString(2, "%" + keywords+ "%");
        pst.setString(3, "%" + keywords + "%");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Borrower borrorwer = new Borrower();
            borrorwer.setId(rs.getInt("id"));
            borrorwer.setName(rs.getString("name"));
            borrorwer.setEmail(rs.getString("email"));
            borrorwer.setAddress(rs.getString("Address"));
            borrorwer.setContactN(rs.getString("ContactN"));

            borrowers.add(borrorwer);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return borrowers;
}
}
