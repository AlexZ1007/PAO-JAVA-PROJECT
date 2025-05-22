package Item;

public class Book extends Item {
    private int yearOfPublication;
    private int numberOfPages;

    public Book(int id, int startingPrice, String name, String author, int yearOfPublication, int numberOfPages) {
        super(id, startingPrice, name, author);
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

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }
}
