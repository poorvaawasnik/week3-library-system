package library;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Library {
    private List<Book> books;
    private List<Member> members;
    private FileHandler fileHandler;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
        this.fileHandler = new FileHandler();
        loadData();
    }

    private void loadData() {
        books = fileHandler.loadBooks();
        members = fileHandler.loadMembers();
        System.out.println("Loaded " + books.size() + " books and " + members.size() + " members");
    }

    public void addBook(Book book) {
        books.add(book);
        fileHandler.saveBooks(books);
        System.out.println("Book added successfully: " + book.getTitle());
    }

    public void removeBook(String isbn) {
        Book bookToRemove = findBookByIsbn(isbn);
        if (bookToRemove != null) {
            books.remove(bookToRemove);
            fileHandler.saveBooks(books);
            System.out.println("Book removed successfully");
        } else {
            System.out.println("Book not found with ISBN: " + isbn);
        }
    }

    public Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Book> searchBooks(String keyword) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                        book.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        System.out.println("\n=== ALL BOOKS ===");
        System.out.println("Total books: " + books.size());
        System.out.println("-".repeat(80));
        for (int i = 0; i < books.size(); i++) {
            System.out.println((i + 1) + ". " + books.get(i));
        }
    }

    public void registerMember(Member member) {
        members.add(member);
        fileHandler.saveMembers(members);
        System.out.println("Member registered successfully: " + member.getName());
    }

    public Member findMemberById(String id) {
        return members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void borrowBook(String isbn, String memberId) {
        Book book = findBookByIsbn(isbn);
        Member member = findMemberById(memberId);

        if (book == null) { System.out.println("Book not found!"); return; }
        if (member == null) { System.out.println("Member not found!"); return; }
        if (!book.isAvailable()) { System.out.println("Book is already borrowed!"); return; }

        book.setAvailable(false);
        book.setBorrowedBy(memberId);
        book.setDueDate(java.time.LocalDate.now().plusWeeks(2));
        member.borrowBook(isbn);

        fileHandler.saveBooks(books);
        fileHandler.saveMembers(members);
        System.out.println("Book borrowed successfully!");
        System.out.println("Due date: " + book.getDueDate());
    }

    public void returnBook(String isbn) {
        Book book = findBookByIsbn(isbn);
        if (book == null || book.isAvailable()) {
            System.out.println("Invalid return request.");
            return;
        }
        Member member = findMemberById(book.getBorrowedBy());
        if (member != null) member.returnBook(isbn);

        book.setAvailable(true);
        book.setBorrowedBy(null);
        book.setDueDate(null);

        fileHandler.saveBooks(books);
        fileHandler.saveMembers(members);
        System.out.println("Book returned successfully!");
    }

    public void displayStatistics() {
        long availableBooks = books.stream().filter(Book::isAvailable).count();
        long borrowedBooks = books.size() - availableBooks;

        System.out.println("\n=== LIBRARY STATISTICS ===");
        System.out.println("Total Books: " + books.size());
        System.out.println("Available Books: " + availableBooks);
        System.out.println("Borrowed Books: " + borrowedBooks);
        System.out.println("Registered Members: " + members.size());

        if (borrowedBooks > 0) {
            long overdueBooks = books.stream()
                    .filter(book -> !book.isAvailable() && book.isOverdue())
                    .count();
            System.out.println("Overdue Books: " + overdueBooks);
        }
    }
}
