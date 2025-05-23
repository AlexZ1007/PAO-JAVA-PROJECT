package Auction;

public class Bid {
    private int userID;
    private int itemID;
    private int amount;

    public Bid(int userID, int itemID, int amount) {
        this.userID = userID;
        this.itemID = itemID;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "userID=" + userID +
                ", itemID=" + itemID +
                ", amount=" + amount +
                '}';
    }

    public int  getUserID() {
        return userID;
    }
    public int getItemID() {
        return itemID;
    }
    public int getAmount() {
        return amount;
    }
}
