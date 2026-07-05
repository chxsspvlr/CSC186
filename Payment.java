public class Payment
{
    private double amountPaid;
    private double balance;
    private int lateDays;
    private double fineAmount;

    // Constructor
    public Payment(double amountPaid){
        this.amountPaid = amountPaid;
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
    public double processPayment(){
        if(lateDays <= 7)
        {
            this.fineAmount = 0.0;
        }
        else
        {
            this.fineAmount = 2.0 + ((lateDays - 8) * 0.80);
        }
        return  this.balance= (this.amountPaid - this.fineAmount);
    }
    
    public void paymentDisplay(){
        System.out.println("Late Days      : " + this.lateDays);
        System.out.printf("Fine Amount    : RM%.2f%n", this.fineAmount);
        System.out.printf("Amount Paid    : RM%.2f%n", this.amountPaid);
        System.out.printf("Balance        : RM%.2f%n", this.balance);
        System.out.println("Payment Status : SUCCESS");
        System.out.println("=====================================");
    }
    
    public boolean paymentStatus()
    {
        if(balance >= 0)
        {
            return true;
        }
        else
        {
            System.out.println("Amount Owing   : RM" +Math.abs(this.balance)); //flips negative sign to positive
            System.out.println("Payment Status : INSUFFICIENT PAYMENT");
            System.out.println("=====================================");
            return false;
        }  
    }
}