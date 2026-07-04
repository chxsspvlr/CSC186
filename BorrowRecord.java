import java.util.*;
public class BorrowRecord
{
    //Attributes
    private String borrowDateStr; //format: DD/MM/YYYY
    private String returnDateStr; //format: DD/MM/YYYY
    private String status;
    private String[] borrowReceipt;
    private String[] custStatus;
    private int[] bookList;
    private int totalBooks;
    private User staff;
    private Customer customer;
    private int totalDueDays;
    private int totalReturnDays;
    private int lateDays;
    private Payment payment;
    
    public static int availability = 0; // shared index: receipt index & returning customer index
    public static int z = 0;
    
    //Normal Constructor
    public BorrowRecord(String bd, User s, Customer c)
    {
        this.borrowDateStr = bd;
        this.staff = s;
        this.customer = c;
    }
    
    //Default Constructor
    public BorrowRecord()
    {
        this.totalBooks = 0;
        this.lateDays = 0;
        this.returnDateStr = null;
        this.status = null;
        this.bookList = new int[5];
        this.custStatus = new String[100];
        this.borrowReceipt = new String[100];
    }
    
    //mutator
    public void setBorrowDate(String borrowDate){ this.borrowDateStr = borrowDateStr; }
    public void setReturnDate(String returnDate){ this.returnDateStr = returnDateStr; }
    public void setStatus(String status){ this.status = status; }
    public void setBookList(int[] bookList){ this.bookList = bookList; }
    public void setTotalBooks(int totalBooks){ this.totalBooks = totalBooks; }
    public void setStaff(User Staff){ this.staff = Staff; }
    public void setCustomer(Customer customer){ this.customer = customer; }
    
    //accessor
    public String getBorrowDate(){ return this.borrowDateStr; }
    public String getReturnDate(){ return this.returnDateStr; }
    public String getStatus(){ return this.status; }
    public int[] getBookList(){ return this.bookList; }
    public int getTotalBooks(){ return this.totalBooks; }
    public User getStaff(){ return this.staff; }
    public Customer getCustomer(){ return this.customer;  }
    public int getTotalDueDays(){ return this.totalDueDays; }
    public int getTotalReturnDays(){ return this.totalReturnDays; }

    public String getReceiptCodes(){ return this.borrowReceipt[z]; }
    
    // ================= RECEIPT ================= (Generates a random receipt code, stores in current availability index)
    public String borrowReceipt()
    {
        Random random = new Random();
        int randNum = random.nextInt(100000,1000000);
        String rCodes = "R#"+randNum;
        
        borrowReceipt[availability] = rCodes;
        return rCodes;
    }
    
    // ================= CUSTOMER STATUS =================
    public void updateStatus()
    {
        if(availability % 2 != 0)
        {
            this.custStatus[availability] = "ACTIVE"; //Generate a new index for each customer who borrows a book.
            availability++;
        }
        else
        {
            this.custStatus[z] = "COMPLETE"; //Z is the index indicating the customer who returned the book.
        }
    }
    
    // ================= DATE STRING CONVERTER =================
    public int DateInt(String dateInput) // To convert String to Integer
    {
        try
        {
            String date = dateInput+"/"; // "20/06/2026/"
            String day = date.substring(0,2); // "20"
            String month = date.substring(3,5); // "06"
            String year = date.substring(6,10); // "2026"
            return  Integer.parseInt(day+month+year); // 20062026
        }
        catch(Exception e)
        { 
            System.err.println("Invalid Date."); 
            return 0;
        }
    }
    
    // ================= DATE CALCULATION =================
    private int totalDays(String dateInput)
    {
        int DateInt = DateInt(dateInput);
        
        int day = DateInt/1000000;
        int month = (DateInt/10000)%100;
        int year = DateInt % 10000;
        
        boolean valid = true; 
        
        if(day < 1 || day > 31){ valid = false;}
        else if(month < 1 || month > 12){ valid = false;}
        else if(month == 2 && day > 28 && year % 4 != 0){ valid = false;}
        else if(month == 2 && day > 29 && year % 4 == 0){ valid = false;}
        else if((month == 4 || month == 6 || month == 9 || month == 11) && day > 30){ valid = false;}
        
        if(!valid)
        {   
            System.out.println("Enter a valid date.");
            return -1;
        }
        
        if(month == 1 || month == 3 || month == 5 || month == 7 ||
        month == 8 || month == 12 || month == 10){day += 31;}
        else if(month == 2 && year % 4 == 0){ day += 29; }
        else if(month == 2){ day += 28; }
        else { day += 30; }

        return year * 365 + day;
    }
    
    // ================= TRACK BORROW DATE =================
    public void trackDueRecord(String borrowDateInput)
    {
        int days = totalDays(borrowDateInput);  
        if( days == -1){ return; }
        this.totalDueDays = days;
    }
    
    // ================= TRACK RETURN DATE =================
    public void trackReturnRecord(String returnDateInput, String receiptCodes)
    {
        // 1- Validate Receipt codes
        boolean found = false;
        for(int x=0; x<availability; x++)
        {
            String codes = "(?i).*"+receiptCodes+".*";
            if(borrowReceipt[x].matches(codes))
            {
                z = x;
                found = true;
            }
        }
        
        if(!found) // if receipt codes not found
        {
            System.out.println("Receipt Codes not found.");
            return;
        }
        
        // 2- Validate return days
        int days = totalDays(returnDateInput);
        if( days == -1){ return; }
        
        // 3- Ensure return days > due days
        if(days < this.totalDueDays)
        {
            System.out.println("Date must exceed borrow date");
            return;
        }
        
        returnBook();
        updateStatus();
        this.totalReturnDays = days;
    }
    
    public void calculateLateDays()
    {
        this.lateDays = (this.totalReturnDays > this.totalDueDays) ? this.totalReturnDays-this.totalDueDays : 0; 
    }
    
    // ================= ADD BOOK TO CART =================
    public void addBook(int bookIndex)
    {
        int index = bookIndex-1;
        
        //Validate input index
        if(index < 0 || index >= Book.bookName.length)
        {
            System.out.println("Invalid input, please re-enter.");
            return;
        }
        else if(Book.available[index] == false) // check book availability
        {
            System.out.println(Book.bookName[index]+" is not available.");
            return;
        }
        
        //Cart limit  
        int maxCart = (this.customer.getMembership() == 'Y')?5:3;

        //Add book into cart (index)
        if(this.totalBooks >= maxCart)
        {
            System.out.println("Cart is full.");  
            return;
         }
        
        this.bookList[this.totalBooks] = bookList[index];
        Book.available[index] = false;
        this.totalBooks++;
        
        System.out.println("- "+Book.bookName[index]+" added! ");
    }
    
    // ================= REMOVE BOOK FROM CART =================
    public void removeBook(int removeBookIndex)
    {
        try
        {
            int index = removeBookIndex-1;
            int position = this.bookList[index]; //To get the actual cart index
            String bkName = Book.bookName[position];
            
            //Validate input index
            if(index < 0 || index >= this.totalBooks)
            {
                System.out.println("Invalid input, please re-enter.");
                return;
            }
            // check book availability in cart
            else if(Book.available[position] == true)
            {
                System.out.println(bkName+" is not borrowed.");
                return;
            }
            
            //To re-arrange the cart index position
            for(int x=index; x<this.totalBooks-1; x++)
            {
                this.bookList[x] = this.bookList[x+1];
            }
            this.totalBooks--; //Remove the last book, as it done replace-copy
            
            Book.available[position] = true;
            System.out.println("Removes ["+bkName+"] from cart. .");
        }
        catch(Exception e){System.err.println("Invalid input.");}
    }
    
    private void displayCart()
    {
        int count = 0;
        System.out.println("\nBooks in Cart:");
        for(int x=0; x<this.totalBooks; x++)
        {
            count++;
            int z = this.bookList[x];
            System.out.println(count+". "+Book.bookName[z]+" By ["+Book.author[z]+"]");
        }
        System.out.println("Total Books :"+count);
        System.out.println("=====================================");
    }
    
    // ================= CONFIRM BORROW =================
    public boolean confirmBorrow()
    {
        Scanner in = new Scanner(System.in);
        
        if(this.totalBooks <= 0)
        {
            System.out.println("Cart is empty.");
            return false;
        }
        
        displayCart();
        System.out.println("Confirm Borrow? (Y/N) :");
        char opt = in.next().toUpperCase().charAt(0);
        
        if(opt == 'Y')
        {
            System.out.println("=====================================");
            System.out.println("          BORROW CONFIRMED");
            System.out.println("=====================================");
            System.out.println("User ID     : "+this.customer.getCustCodes()); //z act as an index in customer array
            System.out.println("Customer    : "+this.customer.getCustName());
            System.out.println("Phone Number: "+this.customer.getCustPhone());
            displayCart();
            System.out.println("Borrow Date : "+this.borrowDateStr);
            updateStatus();
            System.out.println("Status      : ACTIVE");
            System.out.println("=====================================");
            return true;
        }
        else if(opt == 'N')
        {
            System.out.println("=====================================");
            System.out.println("          BORROW CANCELLED");
            System.out.println("=====================================");
            displayCart();
            returnBook();
            System.out.println("  PROCESSING      : CANCELLED");
            System.out.println("=====================================");
            return false;
        }
        else
        { 
            System.out.println("invalid option."); 
            return false;
        }
    }

    // ================= RETURN BOOK =================
    public void returnBook()
    {
        int position=0;
        for(int x=0; x<this.totalBooks; x++)
        {
            Book.available[this.bookList[x]] = true;
        }
    }
}