package cholog;

import jakarta.persistence.*;

// 조인 테이블 컬럼 추가 시 유지보수성을 높이기 위해 조인 테이블 엔티티를 따로 정의한다.
@Entity
public class BookAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Book book;
    @ManyToOne
    private Author author;

    protected BookAuthor() {}

    public BookAuthor(Book book, Author author) {
        this.book = book;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Author getAuthor() {
        return author;
    }
}
