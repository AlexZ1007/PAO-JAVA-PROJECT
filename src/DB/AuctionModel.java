package DB;

import Auction.Auction;
import Auction.Bid;
import Item.*;
import java.sql.*;
import java.util.*;

public class AuctionModel {

    public static int create(Auction auction, String name) {
        String insertAuction = "INSERT INTO auctions (name) VALUES (?)";
        String insertAuctionItems = "INSERT INTO auction_items (auction_id, item_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement auctionStmt = conn.prepareStatement(insertAuction, Statement.RETURN_GENERATED_KEYS)) {

            auctionStmt.setString(1, name);
            auctionStmt.executeUpdate();

            ResultSet keys = auctionStmt.getGeneratedKeys();
            if (keys.next()) {
                int auctionId = keys.getInt(1);

                try (PreparedStatement linkStmt = conn.prepareStatement(insertAuctionItems)) {
                    for (Item item : auction.getItems()) {
                        linkStmt.setInt(1, auctionId);
                        linkStmt.setInt(2, item.getId());
                        linkStmt.addBatch();
                    }
                    linkStmt.executeBatch();
                }

                return auctionId;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static boolean update(Auction auction, int auctionId, String name) {
        String updateAuction = "UPDATE auctions SET name = ? WHERE auction_id = ?";
        String deleteAuctionItems = "DELETE FROM auction_items WHERE auction_id = ?";
        String insertAuctionItems = "INSERT INTO auction_items (auction_id, item_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateAuction);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteAuctionItems);
             PreparedStatement insertStmt = conn.prepareStatement(insertAuctionItems)) {

            updateStmt.setString(1, name);
            updateStmt.setInt(2, auctionId);
            updateStmt.executeUpdate();

            deleteStmt.setInt(1, auctionId);
            deleteStmt.executeUpdate();

            for (Item item : auction.getItems()) {
                insertStmt.setInt(1, auctionId);
                insertStmt.setInt(2, item.getId());
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static HashMap<Integer, String> getAllAuctions() {
        HashMap<Integer, String> auctions = new HashMap<>();
        String sql = "SELECT auction_id, name FROM auctions";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                auctions.put(rs.getInt("auction_id"), rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return auctions;
    }

    public static boolean delete(int auctionId) {
        String deleteAuctionItems = "DELETE FROM auction_items WHERE auction_id = ?";
        String deleteAuction = "DELETE FROM auctions WHERE auction_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement deleteItemsStmt = conn.prepareStatement(deleteAuctionItems);
             PreparedStatement deleteAuctionStmt = conn.prepareStatement(deleteAuction)) {

            deleteItemsStmt.setInt(1, auctionId);
            deleteItemsStmt.executeUpdate();

            deleteAuctionStmt.setInt(1, auctionId);
            deleteAuctionStmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Item> getItems(int auctionId) {
        ArrayList<Item> items = new ArrayList<>();
        String sql = "SELECT i.* FROM items i " +
                "JOIN auction_items ai ON i.item_id = ai.item_id " +
                "WHERE ai.auction_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, auctionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("item_id");
                int price = rs.getInt("starting_price");
                String name = rs.getString("name");
                String author = rs.getString("author");
                String itemType = rs.getString("item_type");

                switch (itemType) {
                    case "book":
                        int yop = rs.getInt("year_of_publication");
                        int nop = rs.getInt("number_of_pages");
                        items.add(new Book(id, price, name, author, yop, nop));
                        break;
                    case "painting":
                        items.add(new Painting(id, price, name, author, rs.getString("type")));
                        break;
                    case "statue":
                        items.add(new Statue(id, price, name, author, rs.getString("material")));
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static ArrayList<Bid> getBids(int auctionId) {
        ArrayList<Bid> bids = new ArrayList<>();
        String sql = "SELECT b.* FROM bids b " +
                "JOIN auction_items ai ON b.item_id = ai.item_id " +
                "WHERE ai.auction_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, auctionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                bids.add(new Bid(
                        rs.getInt("user_id"),
                        rs.getInt("item_id"),
                        rs.getInt("amount")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bids;
    }
}
