package Item;

public class Painting extends Item {
    String type;

    public Painting(int startingPrice, String name, String author, String type) {
        super(startingPrice, name, author);
        this.type = type;
    }

    @Override
    public String toString() {
        return "Painting{" +
                "type='" + type + '\'' +
                super.toString() +
                '}';
    }
}
