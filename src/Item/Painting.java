package Item;

public class Painting extends Item {
    String type;

    public Painting(int id, int startingPrice, String name, String author, String type) {
        super(id, startingPrice, name, author);
        this.type = type;
    }

    @Override
    public String toString() {
        return "Painting{" +
                "type='" + type + '\'' +
                super.toString() +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
