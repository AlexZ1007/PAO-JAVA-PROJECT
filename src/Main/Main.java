package Main;

import Audit.Audit;
import Services.Service;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String command;
        Service service = new Service();
        int userId =-1;
        Scanner scanner = new Scanner(System.in);
        String[] generalCommands={
                "------UNREGISTERED------",
                "help - view help",
                "login - login",
                "exit - exit ",
        };
        String[] userCommands={
                "------USER------",
                "logout - logout ",
                "bidItem - add a bid for an item",
                "listItems - list all items",
                "listAuctionItems - list all items in the auction",
                "changeName - change the name of the current user",
                "auctionPrintWinners - print the winners of the auction",
                "removeMyBids - remove all my bids",
        };
        String[] adminCommands={
                "------ADMIN------",
                "addUser - add a user",
                "openAuction - open the auction",
                "closeAuction - close the auction",
                "loadAuction - load the data from a previous auction",
                "saveAuction - save the auction",
                "deleteAuction - delete the auction",
                "createItem - Create a new item",
                "updateItem - Update an existing item",
                "deleteItem - Delete an existing item",
                "addItem - add an item in the auction",
                "deleteUser - delete a user",
                "listUsers - list all users",

        };
        System.out.println("Welcome!");
        System.out.println("Type help to view the available commands!");
        do{
            System.out.println("Enter command: ");
            command = scanner.nextLine();

            switch (command) {
                case "login":
                    Audit.logAction("login");
                    if(userId != -1){
                        System.out.println("Already logged in!");
                        break;
                    }

                    System.out.println("    Enter username: ");
                    String username = scanner.nextLine();
                    System.out.println("    Enter password: ");
                    String password = scanner.nextLine();
                    userId = service.login(username, password);
                    if(userId == -1)
                        System.out.println("    Invalid username or password!");
                    else
                        System.out.println("    User logged in! ");
                    break;
                case "logout":
                    Audit.logAction("logout");
                    System.out.println("    Log out!");
                    userId = -1;
                    break;
                case "help":
                    Audit.logAction("help");
                    for (String cmd : generalCommands)
                        System.out.println(cmd);
                    if (userId != -1)
                        for (String cmd : userCommands)
                            System.out.println(cmd);
                    if (service.getUserRole(userId).equals("admin"))
                        for (String cmd : adminCommands)
                            System.out.println(cmd);
                    break;
                case "addItem":
                    Audit.logAction("addItem");
                    if (!service.getUserRole(userId).equals("admin")) {
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.addItem();
                    break;
                case "createItem":
                    Audit.logAction("createItem");
                    if (!service.getUserRole(userId).equals("admin")) {
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.createItem();
                    break;
                case "updateItem":
                    Audit.logAction("updateItem");
                    if (!service.getUserRole(userId).equals("admin")) {
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.updateItemStartingPrice();
                    break;
                case "deleteItem":
                    Audit.logAction("deleteItem");
                    if (!service.getUserRole(userId).equals("admin")) {
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.deleteItem();
                    break;
                case "bidItem":
                    Audit.logAction("bidItem");
                    if (service.getUserRole(userId).equals("unregistered")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.addBid(userId);
                    break;
                case "removeMyBids":
                    Audit.logAction("removeMyBids");
                    if (service.getUserRole(userId).equals("unregistered")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.removeAllBidsByUser(userId);
                    break;
                case "listAuctionItems":
                    Audit.logAction("listAuctionItems");
                    if (service.getUserRole(userId).equals("unregistered")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.listAuctionItems();
                    break;
                case "listItems":
                    Audit.logAction("listItems");
                    if (service.getUserRole(userId).equals("unregistered")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.listItems();
                    break;
                case "openAuction":
                    Audit.logAction("openAuction");
                    if (!service.getUserRole(userId).equals("admin")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.openAuction();
                    break;
                case "closeAuction":
                    Audit.logAction("closeAuction");
                    if (!service.getUserRole(userId).equals("admin")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.closeAuction();
                    break;
                case "saveAuction":
                    Audit.logAction("saveAuction");
                    if (!service.getUserRole(userId).equals("admin")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.saveAuction();
                    break;
                case "deleteAuction":
                    Audit.logAction("deleteAuction");
                    if (!service.getUserRole(userId).equals("admin")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.deleteAuction();
                    break;
                case "loadAuction":
                    Audit.logAction("loadAuction");
                    if (!service.getUserRole(userId).equals("admin")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.loadAuction();
                    break;
                case "auctionPrintWinners":
                    Audit.logAction("auctionPrintWinners");
                    if (service.getUserRole(userId).equals("unregistered")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.auctionPrintWinners();
                    break;
                case "addUser":
                    Audit.logAction("addUser");
                    if (!service.getUserRole(userId).equals("admin")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.addUser();
                    break;
                case "changeName":
                    Audit.logAction("changeName");
                    if (service.getUserRole(userId).equals("unregistered")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.changeName(userId);
                    break;
                case "listUsers":
                    Audit.logAction("listUsers");
                    System.out.println(service.getUserRole(userId));
                    if (!service.getUserRole(userId).equals("admin")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.listUsers();
                    break;
                case "deleteUser":
                    Audit.logAction("deleteUser");
                    if (!service.getUserRole(userId).equals("admin")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.deleteUser(userId);
                    break;
                case "exit":
                    Audit.logAction("exit");
                    break;
                default:
                    System.out.println("Invalid command");
            }

        } while (!command.equals("exit"));
        scanner.close();

    }
}
