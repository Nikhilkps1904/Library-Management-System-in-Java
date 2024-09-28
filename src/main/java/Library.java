import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Library {
    private MongoCollection<Document> collection;

    public Library() {
        this.collection = DatabaseConnection.getDatabase().getCollection("books");
    }

    public void addBook(Book book) {
        collection.insertOne(book.toDocument());
    }

    public void removeBook(Book book) {
        collection.deleteOne(Filters.eq("isbn", book.getIsbn()));
    }

    public Book findBook(String isbn) {
        Document doc = collection.find(Filters.eq("isbn", isbn)).first();
        return doc != null ? new Book(doc) : null;
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        for (Document doc : collection.find()) {
            books.add(new Book(doc));
        }
        return books;
    }

    public void displayAvailableBooks() {
        for (Document doc : collection.find(Filters.eq("available", true))) {
            System.out.println(new Book(doc));
        }
    }

    public boolean lendBook(String isbn) {
        Book book = findBook(isbn);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            collection.updateOne(Filters.eq("isbn", isbn), new Document("$set", new Document("available", false)));
            return true;
        }
        return false;
    }

    public boolean returnBook(String isbn) {
        Book book = findBook(isbn);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            collection.updateOne(Filters.eq("isbn", isbn), new Document("$set", new Document("available", true)));
            return true;
        }
        return false;
    }

    public List<Book> searchBooks(String query) {
        List<Book> results = new ArrayList<>();
        Pattern pattern = Pattern.compile(query, Pattern.CASE_INSENSITIVE);

        for (Document doc : collection.find(Filters.or(
                Filters.regex("title", pattern),
                Filters.regex("author", pattern)
        ))) {
            results.add(new Book(doc));
        }

        return results;
    }
}