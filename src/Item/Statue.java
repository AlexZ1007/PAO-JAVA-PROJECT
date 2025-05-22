package Item;

public class Statue extends Item {
    String material;

    public Statue(int id, int startingPrice, String name, String author, String material) {
        super(id, startingPrice, name, author);
        this.material = material;
    }

    @Override
    public String toString() {
        return "Painting{" +
                "material='" + material + '\'' +
                super.toString() +
                '}';
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
