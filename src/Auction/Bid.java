package Auction;

public class Bid {
    private int userID;
    private int itemID;
    private int amount;

    public Bid(int userID, int itemID, int amount) {
        this.userID = userID;
        this.itemID = itemID;
        this.amount = amount;
        System.out.println("New Bid created");
    }

    @Override
    public String toString() {
        return "Bid{" +
                "userID=" + userID +
                ", itemID=" + itemID +
                ", amount=" + amount +
                '}';
    }

    int getUserID() {
        return userID;
    }
    int getItemID() {
        return itemID;
    }
    int getAmount() {
        return amount;
    }
}
