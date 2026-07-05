import java.util.*;
import java.io.*;
public class Librarian extends User {
    private String staffID;
    private String password;
    
    public static final String[] staffID2  = {"S01",   "S02",   "S03", "S04"  };
    public static final String[] password2 = {"abc0", "abc1", "abc2", "abc3"};

    
    public Librarian(String userID, String name, String staffID, String password) {
        super(userID, name);
        this.staffID = staffID;
        this.password = password;
    }

   
    public String getStaffID() { return this.staffID; }
    public String getPassword() { return this.password; }

    
    public void setStaffID(String sID) { this.staffID = sID; }
    public void setPassword(String pswd) { this.password = pswd; }
    
    
    public int findUserID() {
        for (int i = 0; i < userID1.length; i++) {
            if (getUserID().equals(userID1[i])) {
                return i;  
            }
        }
        return -1;
    }

    public boolean checkPassword(int z) {
        return password.equals(password2[z]);
    }

    
    public boolean authenticateStaff(int x) {
        setName(name1[x]);
        setStaffID(staffID2[x]);

        System.out.println("=====================================");
        System.out.println("Welcome Back, " + getName() + "!");
        System.out.println("Staff : " + staffID);
        System.out.println("Role : LIBRARIAN");
        System.out.println("=====================================");
        return true;
    }
     public void generateSummaryReport()
     {
            System.out.println("=====================================================");
            System.out.println("                   SUMMARY REPORT                   ");
            System.out.println("=====================================================");
            System.out.println("No | UserID | Name | Book Code | Title | Borrow Date | Return Date | Status");
            
            int totRecord = 0;
            int totActive = 0;
            
            BorrowRecord br = new BorrowRecord();
            for (int i = 0; i < br.recordCount; i++){
            BorrowRecord br1 = br.allRecords[i];
            totRecord++;

            Customer cust = br1.getCustomer();
            int[] books = br1.getBookList();
            int totalBooks = br1.getTotalBooks();
            
            String returnDate;
            if (br1.getReturnDate() == null) {
                returnDate = "-";
            } else {
                returnDate = br1.getReturnDate();
            }

            
            String status;
            if (br1.getReturnDate() == null) {
                status = "ACTIVE";
            } else {
                status = "COMPLETED";
            }

            if (status.equals("ACTIVE")) {
                totActive++;
            }
            for (int x = 0; x < totalBooks; x++) {
                int bookIndex = books[x];
                Book bk1 = new Book();
                String bookCode = bk1.isbn[bookIndex];
                String title = bk1.bookName[bookIndex];

                if (x == 0) {
                    System.out.println(totRecord + "  | " + cust.getCustCodes() + " | " +
                        cust.getCustName() + " | " + bookCode + " | " + title +
                        " | " + br1.getBorrowDate() + "  | " + returnDate + "  | " + status);
                } else {
                    System.out.println("   |         |      | " + bookCode + " | " + title);
                }
            }
            }
         System.out.println("=====================================================");
        System.out.println("Total Books Currently Borrowed : " + totActive);
        System.out.println("=====================================================");
        }

    public void displayBookCatalogue() {
       
             System.out.println ("=====================================");
             System.out.println ("        BOOK CATALOGUE");
             System.out.println ("=====================================");
             Book bk = new Book ();
                 bk.displayBookInfo();;
    }
    public void WriteFile() throws Exception //WriteFile
    {
        try {
        String userID = "";
        String name = "";
        String staffID = "";
        String password = "";
             Librarian lb = new Librarian (userID,name,staffID, password);
            String[] id1 = lb.userID1;
            String[] nm1 = lb.name1;
            String[] sID = lb.staffID2;
            String[] ps = lb.password2;    

            File fn = new File("UserFile.txt");
            FileWriter fw = new FileWriter(fn);
            PrintWriter pw = new PrintWriter(fw);
            
                for (int i = 0; i < id1.length; i++) {
            pw.print(id1[i] + ";" + nm1[i] + ";" + sID[i] + ";" + ps[i] +  ";");
            }

            pw.close();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void readFile() throws Exception
    {
        try {
            File fn = new File("UserFile.txt");
            FileReader fr = new FileReader("UserFile.txt");                   
            BufferedReader br = new BufferedReader(fr);

            String data = br.readLine();
            
            while (data != null) {
                StringTokenizer token = new StringTokenizer (data,";");
                String userID = token.nextToken();
                String name = token.nextToken();
                String staffID = token.nextToken();
                String password = token.nextToken();
                Librarian lb1 = new Librarian (userID, name, staffID, password);
                System.out.println(lb1.toString());
                data = br.readLine();
        }           
                
    
    }
 catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public String toString() {
        return "\nStaff ID : " + staffID + 
               "\nPassword : " + password;
    }
}