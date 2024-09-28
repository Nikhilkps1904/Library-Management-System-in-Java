import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        logger.info("Starting Library Management System");
        DatabaseConnection.connect();
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        // Add some sample books if the database is empty
        if (library.getAllBooks().isEmpty()) {
            library.addBook(new Book("The Great Gatsby", "F. Scott Fitzgerald", "123456"));
            library.addBook(new Book("To Kill a Mockingbird", "Harper Lee", "234567"));
            library.addBook(new Book("1984", "George Orwell", "345678"));
        }

        while (true) {
            System.out.println("\n1. Display available books");
            System.out.println("2. Lend a book");
            System.out.println("3. Return a book");
            System.out.println("4. Add a new book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Available books:");
                    library.displayAvailableBooks();
                    break;
                case 2:
                    System.out.print("Enter the ISBN of the book you want to borrow: ");
                    String lendIsbn = scanner.nextLine();
                    if (library.lendBook(lendIsbn)) {
                        System.out.println("Book successfully lent.");
                    } else {
                        System.out.println("Book not available or not found.");
                    }
                    break;
                case 3:
                    System.out.print("Enter the ISBN of the book you want to return: ");
                    String returnIsbn = scanner.nextLine();
                    if (library.returnBook(returnIsbn)) {
                        System.out.println("Book successfully returned.");
                    } else {
                        System.out.println("Book return failed. Check the ISBN or if it's already available.");
                    }
                    break;
                case 4:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter book ISBN: ");
                    String isbn = scanner.nextLine();
                    Book newBook = new Book(title, author, isbn);
                    library.addBook(newBook);
                    System.out.println("Book added successfully.");
                    break;
                case 5:
                    System.out.println("Thank you for using the Library Management System. Goodbye!");
                    DatabaseConnection.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
                    logger.info("Library Management System shutting down");
            }
        }
    }
}