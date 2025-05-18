package cholog;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ManyToOneTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    void uniDirection() {
        Publisher publisher = new Publisher("출판사");
        entityManager.persist(publisher);

        Book book = new Book("책", publisher);
        entityManager.persist(book);

        Book persistBook = entityManager.find(Book.class, book.getId());

        assertThat(persistBook).isNotNull();
        assertThat(persistBook.getPublisher()).isNotNull();
    }

    @Test
    void biDirection() {
        Publisher publisher = new Publisher("출판사");
        entityManager.persist(publisher);

        Book book = new Book("책", publisher);
        entityManager.persist(book);
        publisher.addBook(book);

        entityManager.flush();
        entityManager.clear();

        // entityManager로 Find 할 때는 join 없이 (마치 객체 간의 연관관계를 모르는 것처럼) select를 따로 실행함
        Publisher persistPublisher = entityManager.find(Publisher.class, publisher.getId());
        assertThat(persistPublisher).isNotNull();
        // 아래 코드를 주석처리하면 select from publisher 만 실행됨. 즉, 연관되어있는 books 를 select 하지 않음 (Lazy Loading?)
        assertThat(persistPublisher.getBooks()).isNotEmpty();
    }

    @Test
    void findByIdForBook() {
        Publisher publisher = new Publisher("출판사");
        entityManager.persist(publisher);

        Book book = new Book("책", publisher);
        entityManager.persist(book);

        entityManager.flush();
        entityManager.clear();

        // book 조회할 때는 join을 써서 select 실행 (엔티티 사이의 연관 관계를 기반으로 조회)
        // -> book이 연관 관계의 주인이기 때문에, 어떤 publisher와 연결되어 있는지 알고있음 (= 연관 관계를 바로 반영해서 join)
        Optional<Book> persistBook = bookRepository.findById(book.getId());
        assertThat(persistBook).isPresent();
        assertThat(persistBook.get().getPublisher()).isNotNull();
    }

    @Test
    void findByIdForPublisher() {
        Publisher publisher = new Publisher("출판사");
        entityManager.persist(publisher);

        Book book = new Book("책", publisher);
        entityManager.persist(book);

        entityManager.flush();
        entityManager.clear();

        Optional<Publisher> persistPublisher = publisherRepository.findById(publisher.getId());
        assertThat(persistPublisher).isPresent();
        // book 객체의 내부 필드에 직접 접근하기 전까지는 Book 객체를 매핑시키지 않음.
        // -> book 객체를 직접 참조하는 순간, select book (by publisher_id) 을 실행함
        assertThat(persistPublisher.get().getBooks()).isNotNull();
    }
}
