import java.util.ArrayList;

public class Order {
    private int id;
    private ArrayList<Item> items;

    public Order(int id) {
        this.id = id;
        this.items = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public double calculateAverageCost() {
        double totalCost = 0;
        for (Item item : items) {
            totalCost += item.getPrice();
        }
        return totalCost / items.size();
    }
}
