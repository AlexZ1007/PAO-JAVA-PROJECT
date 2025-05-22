package Services;

import Auction.Auction;
import Auction.Bid;
import DB.ItemModel;
import Item.*;
import User.User;

import java.util.*;

import DB.UserModel;

public class Service {
    private Map<Integer, User> users;
    private int userInternalId = 0;
    private Auction auction;
    private ArrayList<Item> items;
    private int itemInternalId = 0;


    // Constructor
    public Service() {
        auction = new Auction();

        users = new HashMap<>();
        for (User user : UserModel.getAllUsers()) {
            users.put(user.getUserId(), user);
        }
        userInternalId=UserModel.getHighestUserId() + 1;

        items = ItemModel.getAllItems();
        itemInternalId=ItemModel.getHighestItemId() + 1;

    }

    // Login method to authenticate users
    public int login(String name, String password) {
        for (User user : users.values()) {
            if (user != null && user.getName().equals(name) && user.checkCredentials(password)) {
                System.out.println(user.getRole());
                return user.getUserId();
            }
        }
        return -1;
    }

    public String getUserRole(int userId) {
        User user = users.get(userId);
        return user != null ? user.getRole() : "unregistered";
    }


    public void createItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert a new item: ");
        System.out.print("Item type (book | painting | statue): ");
        String type = scanner.nextLine().trim().toLowerCase();

        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("Start price: ");
        int startPrice = scanner.nextInt();
        scanner.nextLine(); // consume newline


        switch (type) {
            case "book":
                System.out.print("Year of publication: ");
                int yearOfPublication = scanner.nextInt();
                scanner.nextLine(); // consume newline
                System.out.print("Number of pages: ");
                int numberOfPages = scanner.nextInt();
                scanner.nextLine(); // consume newline
                Book book = new Book(itemInternalId, startPrice, name, author, yearOfPublication, numberOfPages);
                items.add(book);
                ItemModel.createItem(book);
                itemInternalId++;
                break;

            case "painting":
                System.out.print("Painting type: ");
                String paintingType = scanner.nextLine();
                Painting painting = new Painting(itemInternalId, startPrice, name, author, paintingType);
                itemInternalId++;
                items.add(painting);
                ItemModel.createItem(painting);
                break;

            case "statue":
                System.out.print("Material: ");
                String material = scanner.nextLine();
                Statue statue = new Statue(itemInternalId, startPrice, name, author, material);
                itemInternalId++;
                items.add(statue);
                ItemModel.createItem(statue);
                break;

            default:
                System.out.println("Invalid type; insertion failed");
        }
    }

    public void addItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Item id: ");
        int itemId = scanner.nextInt();

        if(itemId < 0 || itemId > items.size()) {
            System.out.println("Invalid item id");
            return;
        }

        Item item = items.get(itemId);
        auction.addItem(item);
        System.out.println("Item added to the auction");

    }

    public void addBid(int userId) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insert a new bid:");


        System.out.print("Item ID: ");
        int itemID = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Bid amount: ");
        int amount = scanner.nextInt();
        scanner.nextLine();

        Bid bid = new Bid(userId, itemID, amount);
        auction.addBid(bid);

    }

    public void listItems() {
        for(int i = 0; i < items.size(); i++){
            System.out.println("Item id: " + i);
            System.out.println(items.get(i).toString());
        }
    }

    public void listAuctionItems(){
        auction.listItems();
    }

    public void openAuction(){
        if(!auction.isOpen()) {
            System.out.println("Opening auction");
            auction.changeState();
        } else
            System.out.println("Auction is already opened");;
    }

    public void closeAuction(){
        if(auction.isOpen()) {
            System.out.println("Closing auction");
            auction.changeState();
        } else
            System.out.println("Auction is already closed");
    }

    public void auctionPrintWinners(){
        auction.printWinners();
    }

    public void addUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert a new user");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Role (admin | user): ");
        String role = scanner.nextLine();

        while(!role.equals("admin") && !role.equals("user")) {
            System.out.println("Invalid role!");
            System.out.println(role.length());;
            System.out.print("Role (admin | user): ");
            role = scanner.nextLine();
        }
        User user = new User(userInternalId, name, password, role);
        users.put(user.getUserId(), user);
        UserModel.createUser(user);

        userInternalId++;
        System.out.println("User created successfully!");
    }

    public void changeName(int userId){

        User user = users.get(userId);
        if (user == null) return;

        Scanner scanner = new Scanner(System.in);
        System.out.print("New name: ");
        String name = scanner.nextLine();

        users.get(userId).setName(name);
        UserModel.updateUser(user);
        System.out.println("Name changed successfully!");
    }

    public void deleteUser(int userId) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("User ID: ");
        int deletedUserId = scanner.nextInt();


        if (!users.containsKey(deletedUserId)) {
            System.out.println("User not found.");
            return;
        }

        if(deletedUserId == userId) {
            System.out.println("Cannot delete yourself!");
            return;
        }

        users.remove(deletedUserId);
        UserModel.deleteUser(deletedUserId);
        System.out.println("User deleted successfully.");

    }


    public void listUsers() {
        if (users.isEmpty()) {
            System.out.println("No users found.");
            return;
        }

        System.out.println("User List:");
        for (User user : users.values()) {
            System.out.println("ID: " + user.getUserId() +
                    " | Name: " + user.getName() +
                    " | Role: " + user.getRole());
        }
    }

    public void deleteItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Item ID: ");
        int itemIndex = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (itemIndex < 0 || itemIndex >= items.size()) {
            System.out.println("Invalid item ID.");
            return;
        }

        Item item = items.get(itemIndex);
        ItemModel.deleteItem(item.getId());
        items.remove(itemIndex);

        auction.deleteItem(item.getId());

        System.out.println("Item deleted successfully.");

    }
    public void updateItemStartingPrice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Item ID: ");
        int itemIndex = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (itemIndex < 0 || itemIndex >= items.size()) {
            System.out.println("Invalid item ID.");
            return;
        }

        Item item = items.get(itemIndex);
        System.out.print("New starting price (current: " + item.getStartingPrice() + "): ");
        int newPrice = scanner.nextInt();
        scanner.nextLine();

        item.setStartingPrice(newPrice);
        ItemModel.updateItem(item);

        System.out.println("Starting price updated successfully.");

        auction.updateItem(item);

    }


}
