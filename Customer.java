import java.util.*;
import java.io.*;
public class Customer extends User
{
    private String pNum;
    private static String[] custCodes = new String[100];
    private static char[] membership = new char[100];
    private String[] memName = new String[100];
    private String[] memPhone = new String[100];
    private static int memSIZE = 0;
    private static String[] custName = new String[100];
    private static String[] custPhone = new String[100];
    public static int SIZE = 0; //Number of customer
    public int index = 0; // Current customer, allow accessor to function
    private static Customer[] allCustomers = new Customer[100];

    // Constructor
    public Customer(String name, String pNum)
    {
        super(generateCustID(), name); //auto-generates customer ID
        this.pNum = pNum;
        saveCustDetails();
    }
    
    // Mutator
    public void setPhoneNumber(String pNum){this.pNum = pNum;}

    // Accessor
    public String getPhoneNumber(){return this.pNum;}
    public char  getMembership(){return this.membership[index]; }
    public String getCustCodes(){return this.custCodes[index];}
    public String getCustName(){return this.custName[index];}
    public String getCustPhone(){return this.custPhone[index];}
    public static String[] getAllCustNames(){ return custName; }
    public static String[] getAllCustPhone(){ return custPhone; }

    
    
    // ================= GENERATE CUSTOMER ID =================
    private static String generateCustID()//(Generates a random customer ID & checks for duplicates)
    {
        Random random = new Random();
        String custID;
        boolean duplicate;
        
        do{
            int randNum = random.nextInt(100,1000);
            custID = "CUS-"+randNum;
            duplicate = false;
            for(int y=0; y<SIZE; y++)
            {
                if(custCodes[y] != null && custCodes[y].equals(custID))
                {
                    duplicate = true;
                    break;
                }
            }
        }while(duplicate);
        
        custCodes[SIZE] = custID;
        membership[SIZE] = 'N'; //default status
        return custID;
    }
    
    // ================= FIND EXISTING CUSTOMER =================
    public static Customer findCustomer(String name, String phone)
    {
        for (int i = 0; i < SIZE; i++)
        {
            if (allCustomers[i] != null &&
                allCustomers[i].getName().equalsIgnoreCase(name) &&
                allCustomers[i].getPhoneNumber().equals(phone))
            {
                return allCustomers[i];
            }
        }
        return null; // not found
    }
    
    // ================= READ MEMBER FILE =================
    public void readFile() throws Exception //(Loads saved name/phone from memberDetails.txt into array)
    {
        try{
            BufferedReader br = new BufferedReader(new FileReader("memberDetails.txt"));
            int idx = 0;

            String data = br.readLine();
            while(data != null)
            {
                StringTokenizer token = new StringTokenizer(data, ";");
                memName[idx] = token.nextToken();
                memPhone[idx] = token.nextToken();
                idx++;
    
                data = br.readLine();
            }
            memSIZE = idx;
            br.close();
        }
        catch(Exception e){
            System.err.println("Error occurred.");
        }
    }
    
    // ================= VERIFY MEMBERSHIP =================
    public boolean verifyMembership()
    {
        try{
            Scanner in = new Scanner(System.in);
            readFile();
        
            System.out.print("Enter Name         : ");
            String name = in.nextLine();

            System.out.print("Enter Phone Number : ");
            String phone = in.nextLine();

            for(int x=0; x<memSIZE; x++)
            {
                if(memName[x].equalsIgnoreCase(name) && memPhone[x].equals(phone)) //loop only in available index array
                {
                    membership[index] = 'Y';
                    return true;
                }
            }

            return false;
        }
        catch(Exception e){System.err.println("Error occurred."); return false;}
    }
    
    // ================= REGISTER MEMBERSHIP =================
    public void registerMembership() //Rewrites a customer ID-> member ID & membership
    {
        try{
            System.out.println("=====================================");
            System.out.println("         REGISTER MEMBERSHIP");
            System.out.println("=====================================");
            System.out.println("  /  Registration Successful!");
            System.out.println("User ID : ");
            System.out.println("Name: "+getName());
            System.out.println("Phone number: +60" +getPhoneNumber());
            System.out.println("=====================================");
        
            Random random = new Random();
            String memID;
            boolean duplicate;
            
            do{
                int randNum = random.nextInt(100,1000);
                memID = "MEM-"+randNum;
                duplicate = false;
                for(int y=0; y<SIZE; y++)
                {
                    if(custCodes[y] != null && custCodes[y].equals(memID))
                    {
                        duplicate = true;
                        break;
                    }
                }
            }while(duplicate);
            
            custCodes[index] = memID;
            membership[index] = 'Y'; //overwrites customer CUS- ID with MEM-ID
            addMember();
        }
        catch(Exception e){System.err.println("Error occurred.");}
    }
    
    // ================= ADD MEMBER TO FILE ================= 
    public void addMember() throws Exception //Add new member into memberDetails.tx, saves it
    {
        try{
            PrintWriter pw = new PrintWriter(new FileWriter("memberDetails.txt", true));

            pw.println(getName()+";"+getPhoneNumber());
            pw.close();
        }
        catch(Exception e){System.err.println("Error occurred.");}
    }

    // ================= UPDATE MEMBER FILE =================  
    public void updateMembertxt() //Update the file, then registers the customer as a new member
    {
        try
        {
            readFile();
            registerMembership();
        }
        catch(Exception e){System.err.println("Error occurred.");}
    }
    
    public void saveCustDetails()//Saves the current customer's details
    {
        index = SIZE;
        custName[index] = getName();
        custPhone[index] = getPhoneNumber();
        allCustomers[index] = this;
        SIZE++;
    }
    
    // Printer
    public String displayCustomer()
    {
        String str = "\nName: " +getName()+
                     "\nPhone Number: " + getPhoneNumber();
        return str;
    }
}