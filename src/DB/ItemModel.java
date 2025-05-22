package DB;

import Item.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemModel {

    public static int createItem(Item item) {
        String sql = "INSERT INTO items (name, author, starting_price, item_type, year_of_publication, number_of_pages, type, material) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getAuthor());
            stmt.setInt(3, item.getStartingPrice());

            if (item instanceof Book) {
                Book book = (Book) item;
                stmt.setString(4, "book");
                stmt.setInt(5, book.getYearOfPublication());
                stmt.setInt(6, book.getNumberOfPages());
                stmt.setString(7, "");
                stmt.setString(8, "");
            } else if (item instanceof Painting) {
                Painting painting = (Painting) item;
                stmt.setString(4, "painting");
                stmt.setInt(5, 0);
                stmt.setInt(6, 0);
                stmt.setString(7, painting.getType());
                stmt.setString(8, "");
            } else if (item instanceof Statue) {
                Statue statue = (Statue) item;
                stmt.setString(4, "statue");
                stmt.setInt(5, 0);
                stmt.setInt(6, 0);
                stmt.setString(7, "");
                stmt.setString(8, statue.getMaterial());
            } else {
                return -1;
            }

            int rows = stmt.executeUpdate();
            if (rows == 0) return -1;

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static ArrayList<Item> getAllItems() {
        ArrayList<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String itemType = rs.getString("item_type");
                int startingPrice = rs.getInt("starting_price");
                String name = rs.getString("name");
                String author = rs.getString("author");
                int itemId = rs.getInt("item_id");

                switch (itemType) {
                    case "book":
                        int yearOfPublication = rs.getInt("year_of_publication");
                        int numberOfPagesr = rs.getInt("number_of_pages");
                        items.add(new Book(itemId, startingPrice, name, author, yearOfPublication, numberOfPagesr));
                        break;
                    case "painting":
                        String paintingType = rs.getString("type");
                        items.add(new Painting(itemId, startingPrice, name, author, paintingType));
                        break;
                    case "statue":
                        String material = rs.getString("material");
                        items.add(new Statue(itemId, startingPrice, name, author, material));
                        break;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static int getHighestItemId() {
        String sql = "SELECT MAX(item_id) AS max_id FROM items";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("max_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static boolean deleteItem(int itemId) {
        String sql = "DELETE FROM items WHERE item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemId);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateItem(Item item) {
        String sql = "UPDATE items SET name = ?, author = ?, starting_price = ? WHERE item_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getName());
            stmt.setString(2, item.getAuthor());
            stmt.setInt(3, item.getStartingPrice());

            stmt.setInt(4, item.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
