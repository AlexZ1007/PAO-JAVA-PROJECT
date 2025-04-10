package Item;

public class Book extends Item {
    private int yearOfPublication;
    private int numberOfPages;

    public Book(int startingPrice, String name, String author, int yearOfPublication, int numberOfPages) {
        super(startingPrice, name, author);
        this.yearOfPublication = yearOfPublication;
        this.numberOfPages = numberOfPages;
    }

    @Override
    public String toString() {
        return "Book{" +
                "numberOfPages=" + numberOfPages +
                ", yearOfPublication='" + yearOfPublication + '\'' +
                super.toString() +
                '}';
    }
}
