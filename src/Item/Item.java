package Item;

public class Item {
    protected int id;
    protected int startingPrice;
    protected String name;
    protected String author;

    public Item(){}

    public Item(int id, int startingPrice, String name, String author) {
        this.id = id;
        this.startingPrice = startingPrice;
        this.name = name;
        this.author = author;
    }

    @Override
    public String toString() {
        return "startingPrice=" + startingPrice +
                ", name='" + name + '\'' +
                ", author='" + author + '\'';
    }

    public int getStartingPrice() {
        return startingPrice;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getId() {
        return id;
    }

    public void setStartingPrice(int startingPrice) {
        this.startingPrice = startingPrice;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
