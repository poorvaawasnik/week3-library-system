package library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String BOOK_FILE = "data/books.txt";
    private static final String MEMBER_FILE = "data/members.txt";

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(BOOK_FILE);

        if (!file.exists()) return books;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 5) continue;
                Book book = new Book(p[0], p[1], p[2], Integer.parseInt(p[3]));
                book.setAvailable(Boolean.parseBoolean(p[4]));
                books.add(book);
            }
        } catch (Exception e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
        return books;
    }

    public void saveBooks(List<Book> books) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOK_FILE))) {
            for (Book b : books) {
                pw.println(String.join(",",
                        b.getIsbn(),
                        b.getTitle(),
                        b.getAuthor(),
                        String.valueOf(b.getYear()),
                        String.valueOf(b.isAvailable())
                ));
            }
        } catch (Exception e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    public List<Member> loadMembers() {
        List<Member> members = new ArrayList<>();
        File file = new File(MEMBER_FILE);

        if (!file.exists()) return members;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length < 2) continue;
                members.add(new Member(p[0], p[1]));
            }
        } catch (Exception e) {
            System.out.println("Error loading members: " + e.getMessage());
        }
        return members;
    }

    public void saveMembers(List<Member> members) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(MEMBER_FILE))) {
            for (Member m : members) {
                pw.println(m.getId() + "," + m.getName());
            }
        } catch (Exception e) {
            System.out.println("Error saving members: " + e.getMessage());
        }
    }
}
