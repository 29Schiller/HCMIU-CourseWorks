
public class Player {
    private String name;
    int money;
    public Player(String name, int money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

        public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void Winbet(int money) {
        this.money += money;
    }
}