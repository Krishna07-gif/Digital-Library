import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private int bookId;
    private String title;
    private String author;
    private String category;
    private boolean isAvailable;

    public Book(int bookId, String title, String author, String category) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.isAvailable = true;
    }

    // Getters and Setters...
      public String getTitle() {
        return title;
    }

    public int getBookId() {
        return bookId;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    // Convert Book object to CSV string
    public String toCsvString() {
        return bookId + "," + title + "," + author + "," + category + "," + isAvailable;
    }

    // Create Book object from CSV string
    public static Book fromCsvString(String csvString) {
    String[] parts = csvString.split(",");
    int bookId = Integer.parseInt(parts[0]);
    String title = parts[1];
    String author = parts[2];
    String category = parts[3];
    boolean isAvailable = Boolean.parseBoolean(parts[4]);
    return new Book(bookId, title, author, category, isAvailable);
}
}

class Member {
    private int memberId;
    private String name;
    private String email;

    public Member(int memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Convert Member object to CSV string
    public String toCsvString() {
        return memberId + "," + name + "," + email;
    }

    // Create Member object from CSV string
    public static Member fromCsvString(String csvString) {
        String[] parts = csvString.split(",");
        int memberId = Integer.parseInt(parts[0]);
        String name = parts[1];
        String email = parts[2];
        return new Member(memberId, name, email);
    }
}

class Library {
    private List<Book> books;
    private List<Member> members;
    private String booksCsvFile = "books.csv";
    private String membersCsvFile = "members.csv";

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addBook(int bookId, String title, String author, String category) {
        Book book = new Book(bookId, title, author, category);
        books.add(book);
        saveBooksToCsv();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void loadBooksFromCsv() {
        try (Scanner scanner = new Scanner(new File(booksCsvFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Book book = Book.fromCsvString(line);
                books.add(book);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Books CSV file not found. Creating a new file.");
        }
    }

    public void saveBooksToCsv() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(booksCsvFile))) {
            for (Book book : books) {
                writer.println(book.toCsvString());
            }
        } catch (IOException e) {
            System.out.println("Failed to write to books CSV file.");
        }
    }

    // Add similar methods for loading and saving members to CSV file...
    public void addMember(int memberId, String name, String email) {
        Member member = new Member(memberId, name, email);
        members.add(member);
        saveMembersToCsv();
    }

    public List<Member> getMembers() {
        return members;
    }

    public void loadMembersFromCsv() {
        try (Scanner scanner = new Scanner(new File(membersCsvFile))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Member member = Member.fromCsvString(line);
                members.add(member);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Members CSV file not found. Creating a new file.");
        }
    }

    public void saveMembersToCsv() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(membersCsvFile))) {
            for (Member member : members) {
                writer.println(member.toCsvString());
            }
        } catch (IOException e) {
            System.out.println("Failed to write to members CSV file.");
        }
    }

}

class Admin {
    private Library library;
    private Scanner scanner;

    public Admin() {
        library = new Library();
        scanner = new Scanner(System.in);
    }

    public void addBook() {
        System.out.println("\nEnter Book Details:");
        System.out.print("Book ID: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Title: ");
        String title = scanner.nextLine();

        System.out.print("Author: ");
        String author = scanner.nextLine();

        System.out.print("Category: ");
        String category = scanner.nextLine();

        library.addBook(bookId, title, author, category);
        System.out.println("Book added successfully!");
    }

    public void addMember() {
        System.out.println("\nEnter Member Details:");
        System.out.print("Member ID: ");
        int memberId = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        library.addMember(memberId, name, email);
        System.out.println("Member added successfully!");
    }

    public void updateBook() {
        System.out.print("Enter Book ID to Update: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); 

        List<Book> books = library.getBooks();
        boolean found = false;

        for (Book book : books) {
            if (book.getBookId() == bookId) {
                System.out.println("Enter Updated Book Details:");
                System.out.print("Title: ");
                String title = scanner.nextLine();

                System.out.print("Author: ");
                String author = scanner.nextLine();

                System.out.print("Category: ");
                String category = scanner.nextLine();

                book.setTitle(title);
                book.setAuthor(author);
                book.setCategory(category);

                System.out.println("Book updated successfully!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found!");
        }
    }

    public void deleteBook() {
        System.out.print("Enter Book ID to Delete: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); 

        List<Book> books = library.getBooks();
        boolean found = false;

        for (Book book : books) {
            if (book.getBookId() == bookId) {
                books.remove(book);
                System.out.println("Book deleted successfully!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found!");
        }
    }

    public void updateMember() {
        System.out.print("Enter Member ID to Update: ");
        int memberId = scanner.nextInt();
        scanner.nextLine(); 

        List<Member> members = library.getMembers();
        boolean found = false;

        for (Member member : members) {
            if (member.getMemberId() == memberId) {
                System.out.println("Enter Updated Member Details:");
                System.out.print("Name: ");
                String name = scanner.nextLine();

                System.out.print("Email: ");
                String email = scanner.nextLine();

                member.setName(name);
                member.setEmail(email);

                System.out.println("Member updated successfully!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Member not found!");
        }
    }

    public void deleteMember() {
        System.out.print("Enter Member ID to Delete: ");
        int memberId = scanner.nextInt();
        scanner.nextLine(); 

        List<Member> members = library.getMembers();
        boolean found = false;

        for (Member member : members) {
            if (member.getMemberId() == memberId) {
                members.remove(member);
                System.out.println("Member deleted successfully!");
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Member not found!");
        }
    }

    public void run() {
        System.out.println("===== Admin Module =====");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Update Book");
            System.out.println("4. Delete Book");
            System.out.println("5. Update Member");
            System.out.println("6. Delete Member");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    addMember();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    updateMember();
                    break;
                case 6:
                    deleteMember();
                    break;
                case 7:
                    System.out.println("Exiting Admin Module...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}



class User {
    private Library library;
    private Scanner scanner;

    public User() {
        library = new Library();
        scanner = new Scanner(System.in);
    }

    public void viewBooks() {
        System.out.println("\nLibrary Books:");
        List<Book> books = library.getBooks();
        for (Book book : books) {
            System.out.println("Book ID: " + book.getBookId());
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("Category: " + book.getCategory());
            System.out.println("Available: " + book.isAvailable());
            System.out.println();
        }
    }

    public void browseByCategory() {
        System.out.print("\nEnter Category to Browse: ");
        String category = scanner.nextLine();

        List<Book> books = library.getBooks();
        boolean found = false;

        System.out.println("\nBooks in Category: " + category);
        for (Book book : books) {
            if (book.getCategory().equalsIgnoreCase(category)) {
                System.out.println("Book ID: " + book.getBookId());
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Available: " + book.isAvailable());
                System.out.println();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found in the given category!");
        }
    }

    public void searchBookByTitle() {
        System.out.print("\nEnter Title to Search: ");
        String searchTitle = scanner.nextLine();

        List<Book> books = library.getBooks();
        boolean found = false;

        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(searchTitle)) {
                System.out.println("Book found!");
                System.out.println("Book ID: " + book.getBookId());
                System.out.println("Title: " + book.getTitle());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Category: " + book.getCategory());
                System.out.println("Available: " + book.isAvailable());
                System.out.println();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found!");
        }
    }

    public void issueBook() {
        System.out.print("\nEnter Book ID to Issue: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); 

        List<Book> books = library.getBooks();
        boolean found = false;

        for (Book book : books) {
            if (book.getBookId() == bookId) {
                if (book.isAvailable()) {
                    book.setAvailable(false);
                    System.out.println("Book issued successfully!");
                } else {
                    System.out.println("Book is not available for issuing!");
                }
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found!");
        }
    }

    public void returnBook() {
        System.out.print("\nEnter Book ID to Return: ");
        int bookId = scanner.nextInt();
        scanner.nextLine(); 
        List<Book> books = library.getBooks();
        boolean found = false;

        for (Book book : books) {
            if (book.getBookId() == bookId) {
                if (!book.isAvailable()) {
                    book.setAvailable(true);
                    System.out.println("Book returned successfully!");
                } else {
                    System.out.println("Book is already available!");
                }
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found!");
        }
    }

    public void sendEmail() {
        System.out.print("\nEnter your query: ");
        String query = scanner.nextLine();

        // Code to send email

        System.out.println("Email sent successfully!");
    }

    public void run() {
        System.out.println("===== User Module =====");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. View Books");
            System.out.println("2. Browse Books by Category");
            System.out.println("3. Search Book by Title");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Send Email");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    browseByCategory();
                    break;
                case 3:
                    searchBookByTitle();
                    break;
                case 4:
                    issueBook();
                    break;
                case 5:
                    returnBook();
                    break;
                case 6:
                    sendEmail();
                    break;
                case 7:
                    System.out.println("Exiting User Module...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}



public class DigiLib {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== Library Management System =====");
        System.out.println("Select an option:");
        System.out.println("1. Admin Module");
        System.out.println("2. User Module");

        int choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice == 1) {
            Admin admin = new Admin();
            admin.run();
        } else if (choice == 2) {
            User user = new User();
            user.run();
        } else {
            System.out.println("Invalid choice. Exiting the program...");
        }
    }
}
