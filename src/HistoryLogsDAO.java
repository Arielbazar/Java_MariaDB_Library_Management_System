import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryLogsDAO {

    private final Connection conn;

    
    public HistoryLogsDAO(Connection conn) {
        this.conn = conn;
    }






    public void addLog(String username, String action) throws SQLException {
        String sql = "INSERT INTO history_logs (username, action, action_date) VALUES (?, ?, NOW())";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, action);
            ps.executeUpdate();
        }
    }

    public List<HistoryLog> getLogsByUser(String username) throws SQLException {
        List<HistoryLog> list = new ArrayList<>();

        String sql = "SELECT * FROM history_logs WHERE username = ? ORDER BY action_date DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HistoryLog log = new HistoryLog(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("action"),
                            rs.getDate("action_date")
                    );
                    list.add(log);
                }
            }
        }

        return list;
    }

     public static List<HistoryLog> getAllHistoryLogs() {

    List<HistoryLog> historyLogs = new ArrayList<>();
    String sql = "SELECT * FROM history_logs";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {

            HistoryLog log = new HistoryLog();
            log.setId(rs.getInt("id"));
            log.setUsername(rs.getString("username"));
            log.setAction(rs.getString("action"));
            log.setAction_date(rs.getDate("action_date"));

            historyLogs.add(log);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return historyLogs;

}
    
}