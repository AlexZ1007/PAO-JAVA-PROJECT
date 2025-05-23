package DB;

import Auction.Bid;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BidModel {

    public static boolean createBid(Bid bid, int auctionId) {
        String sql = "INSERT INTO bids (user_id, item_id, amount, auction_id) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bid.getUserID());
            stmt.setInt(2, bid.getItemID());
            stmt.setInt(3, bid.getAmount());
            stmt.setInt(4, auctionId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Bid> getBidsByAuctionId(int auctionId) {
        ArrayList<Bid> bids = new ArrayList<>();
        String sql = "SELECT bid_id, user_id, item_id, amount FROM bids WHERE auction_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, auctionId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bid bid = new Bid(
                        rs.getInt("user_id"),
                        rs.getInt("item_id"),
                        rs.getInt("amount")
                );
                // Optional: setBidID if method exists
                // bid.setBidID(rs.getInt("bid_id"));
                bids.add(bid);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bids;
    }

    public static boolean updateBidAmount(int bidId, int newAmount) {
        String sql = "UPDATE bids SET amount = ? WHERE bid_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, newAmount);
            stmt.setInt(2, bidId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteBidsFromUser(int userId, int auctionId) {
        String sql = "DELETE FROM bids WHERE user_id = ? AND auction_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, auctionId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static HashMap<String, Integer> getBidIdAndAmount(int auctionId, int userId, int itemId) {
        HashMap<String, Integer> result = new HashMap<>();
        String sql = "SELECT bid_id, amount FROM bids WHERE auction_id = ? AND user_id = ? AND item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, auctionId);
            stmt.setInt(2, userId);
            stmt.setInt(3, itemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                result.put("bid_id", rs.getInt("bid_id"));
                result.put("amount", rs.getInt("amount"));
                return result;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        result.put("bid_id", -1);
        result.put("amount", -1);
        return result;
    }


}
