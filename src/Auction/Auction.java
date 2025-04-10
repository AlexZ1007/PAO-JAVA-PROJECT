package Auction;

import Item.Item;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class Auction {
    boolean open;
    ArrayList<Item> items;
    ArrayList<Bid> bids;

    public Auction(){
        open = false;
        items = new ArrayList<>();
        bids = new ArrayList<>();
    }

    public void changeState(){
        open = !open;
    }

    public void addItem(Item item){
        if(!open){
            items.add(item);
        } else {
            System.out.println("Can't add any more items! The auction is opened!");
        }
    }

    public void addBid(Bid bid){
        if(open){
            if(isBidInvalid(bid)){
                System.out.println("The bid is invalid!");
                return;
            }
            bids.add(bid);
        } else{
            System.out.println("Can't add any more bids! The auction is closed!");
        }
    }

    public void listItems(){
        for(int i = 0; i < items.size(); i++){
            System.out.println("Item id: " + i);
            System.out.println(items.get(i).toString());
        }
    }

    public boolean isOpen(){
        return open;
    }

    public void printWinners() {
        if(open){
            System.out.println("Auction is still open");
            return;
        }

        System.out.println("Winners: ");

        for (int i = 0; i < items.size(); i++) {
            int itemID = i;
            Optional<Bid> maxBid = bids.stream()
                    .filter(b -> b.getItemID() == itemID)
                    .max(Comparator.comparingInt(Bid::getAmount));

            if (maxBid.isPresent()) {
                System.out.println("Max bid for item " + itemID + ": " + "by user with id: " + maxBid.get().getUserID());
            } else {
                System.out.println("No bids found for item " + itemID);
            }
        }
    }

    private boolean isBidInvalid(Bid bid){
        return (bid.getItemID() >= items.size() || bid.getItemID() < 0)
                || bid.getAmount() < 0 || items.get(bid.getItemID()).getStartingPrice() > bid.getAmount();
    }

}
