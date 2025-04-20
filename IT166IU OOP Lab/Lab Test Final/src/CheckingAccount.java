import java.lang.Comparable;

public class CheckingAccount extends Account {
    public double overdraftAmount;

    
    public CheckingAccount(String accountID, String customerID, String accountType, double balance, String password,
            double overdraftAmount) {
        super(accountID, customerID, accountType, balance, password);
        this.overdraftAmount = overdraftAmount;
    }


    @Override
    public boolean withdraw(double amount) {
        if(super.balance < amount){
            if ( (amount - super.balance) > this.overdraftAmount) {
                return false;
            }
            else{
                this.overdraftAmount = this.overdraftAmount - (amount - super.balance);
                super.balance = 0;
                return true;
            }
        }
        else{
            super.balance = super.balance - amount;
            return true;
        }
    }


    public double getOverdraftAmount() {
        return overdraftAmount;
    }


    public void setOverdraftAmount(double overdraftAmount) {
        this.overdraftAmount = overdraftAmount;
    }
    
}
