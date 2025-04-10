package Item;

public class Statue extends Item {
    String material;

    public Statue(int startingPrice, String name, String author, String material) {
        super(startingPrice, name, author);
        this.material = material;
    }

    @Override
    public String toString() {
        return "Painting{" +
                "material='" + material + '\'' +
                super.toString() +
                '}';
    }

}
