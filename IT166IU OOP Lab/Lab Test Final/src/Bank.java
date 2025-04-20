import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String name;
    private String address;
    private List<Account> listofAccount;
    public Bank(String name, String address) {
        this.name = name;
        this.address = address;
        this.listofAccount = new ArrayList<>();
    }
    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public List<Account> getListofAccount() {
        return listofAccount;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setListofAccount(List<Account> listofAccount) {
        this.listofAccount = listofAccount;
    }

    public void addAccount(Account acc){
        if(listofAccount.contains(acc)){
            System.out.println("Couldn't add this account");
        }
        else{
            listofAccount.add(acc);
            System.out.println(acc.getAccountID() + " " + acc.getAccountType() + " " + acc.getCustomerID() + " was added succesfully!");
        }
    }

    public void removeAccount(String accountID){
        for (Account acc : listofAccount){
            if(acc.getAccountID() == accountID){
                listofAccount.remove(acc);
            }
            else{
                System.out.println("Couldn't remove this account");
            }
        }
    }

    public String displayAccbyAccID(Account accountID){
        return address;
    }

    public Account login(String accountID, String password){
        return null;

    }
    @Override
    public String toString() {
        return "Bank [name=" + name + ", address=" + address + ", listofAccount=" + listofAccount + "]";
    }
    
}
