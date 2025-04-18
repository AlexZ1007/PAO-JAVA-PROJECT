package Services;

import Auction.Auction;
import Auction.Bid;
import Item.Item;
import Item.Book;
import Item.Painting;
import Item.Statue;
import User.User;
import java.util.ArrayList;
import java.util.Scanner;

public class Service {
    private ArrayList<User> users;
    private int userInternalId = 0;
    private Auction auction;

    // Constructor
    public Service() {
        users = new ArrayList<>();
        auction = new Auction();
        users.add(new User(userInternalId, "admin", "pass", "admin"));
        userInternalId++;
    }

    // Login method to authenticate users
    public int login(String name, String password) {
        for (User user : users) {
            if (user != null && user.getName().equals(name) && user.checkCredentials(password)) {
                return user.getUserId();
            }
        }
        return -1;
    }

    public String getUserRole(int userId){
        if (userId < 0 || userId >= users.size() || users.get(userId) == null) return "unregistered";
        return users.get(userId).getRole();
    }

    public void addItem() {
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
                auction.addItem(new Book(startPrice, name, author, yearOfPublication, numberOfPages));
                break;

            case "painting":
                System.out.print("Painting type: ");
                String paintingType = scanner.nextLine();
                auction.addItem(new Painting(startPrice, name, author, paintingType));
                break;

            case "statue":
                System.out.print("Material: ");
                String material = scanner.nextLine();
                auction.addItem(new Statue(startPrice, name, author, material));
                break;

            default:
                System.out.println("Invalid type; insertion failed");
        }
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

        users.add(new User(userInternalId, name, password, role));
        userInternalId++;
        System.out.println("User created successfully!");
    }

    public void changeName(int userId){
        if (userId < 0 || userId >= users.size()) return;
        Scanner scanner = new Scanner(System.in);
        System.out.print("New name: ");
        String name = scanner.nextLine();

        users.get(userId).setName(name);
    }

}
