
public class issuedBooks {
    private int id;
    private int bookid;
    private int borrowerid;
    private String borrowername;
    private String title;
    private String issueDate;
    private String returnDate;
    private String status;
    private String actionTime;
    
    public issuedBooks(int id,int bookid, int borrowerid, String borrowername, String title,String issueDate, String returnDate, String status, String actiontime ){
        this.id = id;
        this.bookid = bookid;
        this.borrowerid = borrowerid;
        this.borrowername = borrowername;
        this.title = title;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.actionTime = actionTime;
    }

    public String getBorrowername() {
        return borrowername;
    }

    public void setBorrowername(String borrowername) {
        this.borrowername = borrowername;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    issuedBooks() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getBorrowerid() {
        return borrowerid;
    }

    public void setBorrowerid(int borrowerid) {
        this.borrowerid = borrowerid;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }
}
