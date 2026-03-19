
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class issuedBooksDAO {

    public static boolean borrowBook(int bookId, int borrowerId) {
       try (Connection conn = DBConnect.getConnection()) {
        if(!isAvailable(bookId)) return false;

        String sql = "INSERT INTO issued_books(book_id, borrower_id, issue_date, status) VALUES (?, ?, NOW(), 'borrowed')";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, bookId);
            pstm.setInt(2, borrowerId);
            pstm.executeUpdate();
        }

       
        try (PreparedStatement pstm = conn.prepareStatement("UPDATE book SET quantity = quantity - 1 WHERE id=?")) {
            pstm.setInt(1, bookId);
            pstm.executeUpdate();
        }

              HistoryLogsDAO logDAO = new HistoryLogsDAO(conn);
              logDAO.addLog(Staff_Frame.SaveUsername,HistoryLog.BORROW);
              
                return true;
              
        } catch (Exception e) {
            e.printStackTrace();
             return false;
        }
    }
    
   public static boolean isAvailable(int bookId) {
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement pst = conn.prepareStatement(
             "SELECT quantity FROM book WHERE id=?")) {

        pst.setInt(1, bookId);
       ResultSet  rs = pst.executeQuery();

        if(rs.next()) {
            return rs.getInt("quantity") > 0;
        }
        return false;

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    
    public static boolean hasBorrowed(int borrowerId, int bookId) {
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement pst = conn.prepareStatement(
             "SELECT * FROM issued_books WHERE borrower_id=? AND book_id=? AND status='borrowed'")) {

        pst.setInt(1, borrowerId);
        pst.setInt(2, bookId);

        return pst.executeQuery().next();
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    
    public static boolean ReturnedBook(int bookId, int borrowerId) {
       try (Connection conn = DBConnect.getConnection()) {
        if(!hasBorrowedBook(borrowerId, bookId)) {
            return false;
        }

        String sql = "UPDATE issued_books SET return_date = NOW(), status='returned' WHERE borrower_id=? AND book_id=? AND return_date IS NULL";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, bookId);
            pstm.setInt(2, borrowerId);
            pstm.executeUpdate();
        }

       
        try (PreparedStatement pstm = conn.prepareStatement("UPDATE book SET quantity = quantity + 1 WHERE id=?")) {
            pstm.setInt(1, bookId);
            pstm.executeUpdate();
        }

              HistoryLogsDAO logDAO = new HistoryLogsDAO(conn);
              logDAO.addLog(Staff_Frame.SaveUsername,HistoryLog.RETURN);
              
                return true;
              
        } catch (Exception e) {
            e.printStackTrace();
             return false;
        }
    }
    
    public static boolean hasBorrowedBook(int borrowerId, int bookId) {
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement pst = conn.prepareStatement(
             "SELECT * FROM issued_books WHERE borrower_id=? AND book_id=? AND return_date IS NULL")) {

        pst.setInt(1, borrowerId);
        pst.setInt(2, bookId);

        ResultSet rs = pst.executeQuery();

        return rs.next();

    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
     
     public static boolean hasReturned(int borrowerId, int bookId) {
    try (Connection conn = DBConnect.getConnection();
         PreparedStatement pst = conn.prepareStatement(
             "SELECT * FROM issued_books WHERE borrower_id=? AND book_id=? AND status='returned'")) {

        pst.setInt(1, borrowerId);
        pst.setInt(2, bookId);

        return pst.executeQuery().next();
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
      public static List<issuedBooks> getAllIssuedB() {
    List<issuedBooks> Ib = new ArrayList<>();
    String sql = "SELECT ib.id, ib.book_id, ib.borrower_id,\n" +
"       br.name AS borrower_name,\n" +
"       bk.title AS book_title,\n" +
"       ib.issue_date, ib.return_date, ib.status, ib.Action_time\n" +
"       FROM issued_books ib\n" +
"       JOIN borrower br ON ib.borrower_id = br.id\n" +
"       JOIN book bk ON ib.book_id = bk.id\n" +
"       WHERE ib.status = 'borrowed'";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            issuedBooks ib;
            ib = new issuedBooks();
            ib.setId(rs.getInt("id"));
            ib.setBookid(rs.getInt("book_id"));
            ib.setBorrowerid(rs.getInt("borrower_id"));
            ib.setBorrowername(rs.getString("br.borrower_name"));
            ib.setTitle(rs.getString("bk.book_title"));
            ib.setStatus(rs.getString("status"));
            ib.setIssueDate(rs.getString("issue_date"));
            ib.setReturnDate(rs.getString("return_date"));
            ib.setActionTime(rs.getString("Action_time"));

            Ib.add(ib);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return Ib;
}
      
      public static List<issuedBooks> getAllIssuedR() {
    List<issuedBooks> Ib = new ArrayList<>();
    String sql = "SELECT ib.id, ib.book_id, ib.borrower_id,\n" +
"       br.name AS borrower_name,\n" +
"       bk.title AS book_title,\n" +
"       ib.issue_date, ib.return_date, ib.status, ib.Action_time\n" +
"FROM issued_books ib\n" +
"JOIN borrower br ON ib.borrower_id = br.id\n" +
"JOIN book bk ON ib.book_id = bk.id\n" +
"WHERE ib.status = 'returned'";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            issuedBooks ib;
            ib = new issuedBooks();
            ib.setId(rs.getInt("id"));
            ib.setBookid(rs.getInt("book_id"));
            ib.setBorrowerid(rs.getInt("borrower_id"));
            ib.setBorrowername(rs.getString("br.borrower_name"));
            ib.setTitle(rs.getString("bk.book_title"));
            ib.setStatus(rs.getString("status"));
            ib.setIssueDate(rs.getString("issue_date"));
            ib.setReturnDate(rs.getString("return_date"));
            ib.setActionTime(rs.getString("Action_time"));

            Ib.add(ib);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return Ib;
}
     
}
