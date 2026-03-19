import java.sql.*;
public class HistoryLog {
    private int id;
    private String username;
    private String action;
    private java.sql.Date action_date;
    
    public static final String LOGIN = "Log in";
    public static final String LOGOUT = "Log Out";
    public static final String BORROW = "Borrowed Book";
    public static final String RETURN = "Returned Book";
    public static final String AS = "Added a Staff ";
    public static final String DS = "Deleted a Staff";
    public static final String US = "Updated a Staff";
    public static final String AB = "Added a Book ";
    public static final String DB = "Deleted a Book";
    public static final String UB = "Updated a Book";
    public static final String ABR = "Added a Borrower";
    public static final String UBR = "Updated a Borrower";
    public static final String DBR = "Deleted a Borrower";
    
    


     public HistoryLog( String username, String action, java.sql.Date actionDate){
        this.username = username;
        this.action = action;
        this.action_date = actionDate;
    }
    
    public HistoryLog(int id, String username, String action, java.sql.Date actionDate){
        this.id = id;
        this.username= username;
        this.action = action;
        this.action_date = actionDate;
    }

    HistoryLog() {
      
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getAction_date() {
        return action_date;
    }

    public void setAction_date(Date action_date) {
        this.action_date = action_date;
    }

    void addlog(String username) {
      
    }
    
   
}
