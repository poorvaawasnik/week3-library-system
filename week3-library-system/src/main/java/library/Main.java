package library;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.println("1. Add New Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Books");
            System.out.println("4. Register Member");
            System.out.println("5. Borrow Book");
            System.out.println("6. Return Book");
            System.out.println("7. View Library Statistics");
            System.out.println("8. Exit");
            System.out.print("\nEnter your choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.print("Enter Title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter Author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter Year: ");
                    int year = Integer.parseInt(sc.nextLine());
                    library.addBook(new Book(isbn, title, author, year));
                    break;
                case "2":
                    library.displayAllBooks();
                    break;
                case "3":
                    System.out.print("Enter keyword: ");
                    library.searchBooks(sc.nextLine()).forEach(System.out::println);
                    break;
                case "4":
                    System.out.print("Enter Member ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Member Name: ");
                    String name = sc.nextLine();
                    library.registerMember(new Member(id, name));
                    break;
                case "5":
                    System.out.print("Enter Book ISBN: ");
                    String bisbn = sc.nextLine();
                    System.out.print("Enter Member ID: ");
                    String mid = sc.nextLine();
                    library.borrowBook(bisbn, mid);
                    break;
                case "6":
                    System.out.print("Enter Book ISBN to return: ");
                    library.returnBook(sc.nextLine());
                    break;
                case "7":
                    library.displayStatistics();
                    break;
                case "8":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
