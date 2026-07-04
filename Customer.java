import java.util.*;
import java.io.*;
public class Customer extends User
{
    private String pNum;
    private static String[] custCodes = new String[1000];
    private static char[] membership = new char[1000];
    private String[] memName = new String[1000];
    private String[] memPhone = new String[1000];
    private static int memSIZE = 0;
    private String[] custName = new String[1000];
    private String[] custPhone = new String[1000];
    public static int SIZE = 0;

    // Constructor
    public Customer(String name, String pNum)
    {
        super(generateCustID(), name); //auto-generates customer ID
        this.pNum = pNum;
    }
    
    // Mutator
    public void setPhoneNumber(String pNum){this.pNum = pNum;}

    // Accessor
    public String getPhoneNumber(){return this.pNum;}
    public char  getMembership(){return this.membership[SIZE]; }
    public String getCustCodes(){return this.custCodes[SIZE];}
    public String getCustName(){return this.custName[SIZE];}
    public String getCustPhone(){return this.custPhone[SIZE];}

    // ================= GENERATE CUSTOMER ID =================
    private static String generateCustID()//(Generates a random customer ID & checks for duplicates)
    {
        Random random = new Random();
        int randNum = random.nextInt(100,1000);
        String custID = "CUS-"+randNum;
        
        boolean duplicate = false;
        for(int y=0; y<SIZE; y++)
        {
            if(custCodes[y] != null && custCodes[y].equals(custID))
            {
                duplicate = true;
                break;
            }
        }
        
        custCodes[SIZE] = custID;
        membership[SIZE] = 'N'; //default status
        return custID;
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
            System.out.println(e.getMessage());
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
                    membership[SIZE] = 'Y';
                    saveCustDetails();
                    return true;
                }
            }

            return false;
        }
        catch(Exception e){e.getMessage(); return false;}
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
            int randNum = random.nextInt(100,1000);
            String memID = "MEM-"+randNum;
        
            boolean duplicate = false;
            for(int y=0; y<SIZE; y++)
            {
                if(custCodes[y] != null && custCodes[y].equals(memID))
                {
                    duplicate = true;
                    break;
                }
            }
            
            custCodes[SIZE] = memID;
            membership[SIZE] = 'Y'; //overwrites customer CUS- ID with MEM-ID
            addMember();
            saveCustDetails();
        }
        catch(Exception e){e.getMessage();}
    }
    
    // ================= ADD MEMBER TO FILE ================= 
    public void addMember() throws Exception //Add new member into memberDetails.tx, saves it
    {
        try{
            PrintWriter pw = new PrintWriter(new FileWriter("memberDetails.txt", true));

            pw.print(getName()+";");
            pw.print(getPhoneNumber()+";");
            pw.close();
        }
        catch(Exception e){System.err.println(e.getMessage());}
    }

    // ================= UPDATE MEMBER FILE =================  
    public void updateMembertxt() //Update the file, then registers the customer as a new member
    {
        try
        {
            readFile();
            registerMembership();
        }
        catch(Exception e){e.getMessage();}
    }
    
    public void saveCustDetails()//Saves the current customer's details
    {
        custName[SIZE] = getName();
        custPhone[SIZE] = getPhoneNumber();
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