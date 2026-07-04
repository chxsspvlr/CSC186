public class Payment
{
    private double amountPaid;
    private double balance;
    private int lateDays;
    private double fineAmount;

    // Constructor
    public Payment(double amountPaid, double balance,int lateDays, double fineAmount){
        this.amountPaid = amountPaid;
        this.balance = balance;
        this.lateDays = lateDays;
        this.fineAmount = fineAmount;
    }

    // Mutator
    public void setAmountPaid(double amountPaid){this.amountPaid = amountPaid;}
    public void setBalance(double balance){this.balance = balance;}
    public void setLateDays(int lateDays){this.lateDays = lateDays;}
    public void setFineAmount(double fineAmount){this.fineAmount = fineAmount;}

    // Accessor
    public double getAmountPaid(){return amountPaid;}
    public double getBalance(){return balance;}
    public int getLateDays(){return lateDays;}
    public double getFineAmount(){return fineAmount;}

    // Processor
    public void processPayment(){
    calculateFine();
    calculateBalance();
    updateStatus();
    }
    
    public double calculateBalance(){
        return  this.amountPaid - this.fineAmount ;
    }
    
    public void updateStatus(){
    System.out.println("\n=====================================");
        System.out.println("         PAYMENT RECEIPT");
        System.out.println("=====================================");
        System.out.println("Late Days      : " + this.lateDays);
        System.out.print("Fine Amount    : RM" +this.fineAmount);
        System.out.print("Amount Paid    : RM" +this.amountPaid);

        if(balance >= 0)
        {
            System.out.printf("Balance        : RM", this.balance);
            System.out.println("Payment Status : SUCCESS");
        }
        else
        {
            System.out.println("Amount Owing   : RM" +calculateBalance());
            System.out.println("Payment Status : INSUFFICIENT PAYMENT");
        }

        System.out.println("=====================================");
    }
    
    public double calculateFine(){
      if(lateDays <= 7)
        this.fineAmount = 0.0;
      else
        this.fineAmount = 2.0 + ((lateDays - 8) * 0.80);
      return this.fineAmount;
    }


    // Printer
    public void displayFineAmount()
    {
        System.out.println("Fine Amount: RM" + this.fineAmount);
    }
}