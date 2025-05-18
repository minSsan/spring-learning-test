package cholog;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "publisher")
    private Set<Book> books;

    public Publisher(String name) {
        this.name = name;
        books = new HashSet<>();
    }

    public Publisher() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public Set<Book> getBooks() {
        return Collections.unmodifiableSet(books);
    }
}
