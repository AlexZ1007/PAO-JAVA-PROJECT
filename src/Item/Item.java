package Item;

public class Item {
    protected int startingPrice;
    protected String name;
    protected String author;

    public Item(){}

    public Item(int startingPrice, String name, String author) {
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
}
