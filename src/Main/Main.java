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
                "logout - logout ",
        };
        String[] userCommands={
                "auctions - show the list of auctions",
        };
        String[] adminCommands={
                "addUser - add a user",
        };
        do{
            System.out.println("Enter command: ");
            command = scanner.nextLine();

            switch (command) {
                case "login":
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
//                case "role":
//                    if(userId == -1)
//                        break;
//                    System.out.println("    My role: " + service.getUserRole(userId));
                case "exit":
                    break;
                default:
                    System.out.println("Invalid command");
            }

        } while (!command.equals("exit"));
        scanner.close();

    }
}
