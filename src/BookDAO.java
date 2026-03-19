import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class BookDAO {
    
    
    public static Book getbyId(int id){
        String sql = "SELECT * FROM book WHERE id=?";
        try (Connection conn = DBConnect.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return new Book(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("author"),
                rs.getString("isbn"),
                rs.getInt("quantity"),
                rs.getString("genre")
                );
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean addBook(Book book) {
        String sql = "INSERT INTO book (title, author, isbn,quantity,genre) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            pstmt.setString(3, book.getIsbn());
            pstmt.setInt(4,book.getQuantity());
            pstmt.setString(5, book.getGenre());
              HistoryLogsDAO logDAO = new HistoryLogsDAO(conn);
            logDAO.addLog(AdminFrame.SaveUsername,HistoryLog.AB);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static boolean delBook(String title, String author){
        String sql = "DELETE FROM book WHERE title = ? AND author = ?";
        
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1,title);
            pstmt.setString(2,author);
              HistoryLogsDAO logDAO = new HistoryLogsDAO(conn);
            logDAO.addLog(AdminFrame.SaveUsername,HistoryLog.DB);
            int rows = pstmt.executeUpdate();
            
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("SET @count = 0");
            stmt.executeUpdate("UPDATE book SET id = (@count := @count + 1) ORDER BY id");
            stmt.executeUpdate("ALTER TABLE book AUTO_INCREMENT = 1");
            
            return rows > 0;
        
    }catch (SQLException e){
        e.printStackTrace();
        return false;
    }
}
    
    
     public static boolean updBook(Book book) {
        String sql = "UPDATE  book SET title = ?, author = ?, genre = ?, isbn = ?, quantity = ? WHERE id = ?";

        try (Connection conn = DBConnect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
             pstmt.setString(2, book.getAuthor());
             pstmt.setString(3,book.getGenre());
            pstmt.setString(4, book.getIsbn());
            pstmt.setInt(5,book.getQuantity());
            pstmt.setInt(6,book.getId());
              HistoryLogsDAO logDAO = new HistoryLogsDAO(conn);
            logDAO.addLog(AdminFrame.SaveUsername,HistoryLog.UB);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
     public static List<Book> getAllBook() {
    List<Book> books = new ArrayList<>();
    String sql = "SELECT * FROM book";

    try (Connection conn = DBConnect.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            Book book;
            book = new Book();
            book.setId(rs.getInt("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setGenre(rs.getString("genre"));
            book.setIsbn(rs.getString("isbn"));
            book.setQuantity(rs.getInt("quantity"));

            books.add(book);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return books;
}
     
      public static List<Book> SearchBook(String keyword) {
    List<Book> books = new ArrayList<>();
    String sql = "SELECT * FROM book WHERE title LIKE ? OR author LIKE ? OR genre LIKE ?";

    try {
        Connection conn = DBConnect.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql);

        pst.setString(1, "%" + keyword + "%");
        pst.setString(2, "%" + keyword + "%");
        pst.setString(3, "%" + keyword + "%");

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setTitle(rs.getString("title"));
            book.setAuthor(rs.getString("author"));
            book.setGenre(rs.getString("genre"));
            book.setQuantity(rs.getInt("quantity"));

            books.add(book);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return books;
}

 
}
