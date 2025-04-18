package Main;

import Services.Service;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String command;
        Service service = new Service();
        int userId =-1;
        Scanner scanner = new Scanner(System.in);
        String[] generalCommands={
                "help - view help",
                "login - login",
                "exit - exit ",
        };
        String[] userCommands={
                "logout - logout ",
                "addBid - add a bid for an item",
                "listItems - list all items in the auction",
                "changeName - change the name of the current user",
                "auctionPrintWinners - print the winners of the auction",
        };
        String[] adminCommands={
                "addUser - add a user",
                "openAuction - open the auction",
                "closeAuction - close the auction",
                "addItem - add an item in the auction",

        };
        System.out.println("Welcome!");
        System.out.println("Type help to view the available commands!");
        do{
            System.out.println("Enter command: ");
            command = scanner.nextLine();

            switch (command) {
                case "login":
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
                    System.out.println("    Log out!");
                    userId = -1;
                    break;
                case "help":
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
                    if (!service.getUserRole(userId).equals("admin")) {
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.addItem();
                    break;
                case "bidItem":
                    if (service.getUserRole(userId).equals("unregistered")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.addBid(userId);
                    break;
                case "listItems":
                    if (service.getUserRole(userId).equals("unregistered")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.listAuctionItems();
                    break;
                case "openAuction":
                    if (!service.getUserRole(userId).equals("admin")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.openAuction();
                    break;
                case "closeAuction":
                    if (!service.getUserRole(userId).equals("admin")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.closeAuction();
                    break;
                case "auctionPrintWinners":
                    if (service.getUserRole(userId).equals("unregistered")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.auctionPrintWinners();
                    break;
                case "addUser":
                    if (!service.getUserRole(userId).equals("admin")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.addUser();
                    break;
                case "changeName":
                    if (service.getUserRole(userId).equals("unregistered")){
                        System.out.println("You do not have permission for this command!");
                        break;
                    }
                    service.changeName(userId);
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Invalid command");
            }

        } while (!command.equals("exit"));
        scanner.close();

    }
}
