import org.bson.Document;
import org.bson.types.ObjectId;

public class Book {
    private ObjectId id;
    private String title;
    private String author;
    private String isbn;
    private boolean available;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = true;
    }

    public Book(Document doc) {
        this.id = doc.getObjectId("_id");
        this.title = doc.getString("title");
        this.author = doc.getString("author");
        this.isbn = doc.getString("isbn");
        this.available = doc.getBoolean("available", true);
    }

    public ObjectId getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Document toDocument() {
        return new Document("title", title)
                .append("author", author)
                .append("isbn", isbn)
                .append("available", available);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", available=" + available +
                '}';
    }
}