import java.lang.Comparable;

public class SavingAccount extends Account {
    public double interestRate;

    
    public SavingAccount(String accountID, String customerID, String accountType, double balance, String password,
            double interestRate) {
        super(accountID, customerID, accountType, balance, password);
        this.interestRate = interestRate;
    }

    
    @Override
    public boolean withdraw(double amount) {
        if (super.balance < amount){
            System.out.println("Transaction Fails");
            return false;    
        }
        else{
            System.out.println("Transaction Succeeds");
            super.balance = super.balance - amount;
            return true;
        }
    }

    @Override
    public String toString() {
        return "SavingAccount [interestRate=" + interestRate + "]";
    }


    public double getInterestRate() {
        return interestRate;
    }


    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    
}
