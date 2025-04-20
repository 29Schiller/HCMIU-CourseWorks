public class House {
    int money;

    public House(int money) {
        this.money = money;
    }
    public int getMoney() {
        return money;
    }
    public void addMoney(int money) {
        this.money += money;
    }
    public void subtractMoney(int money) {
        this.money -= money;
    }
}