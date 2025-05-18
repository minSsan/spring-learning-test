package cholog;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 단방향에서는 LAZY 로딩이 먹히는데, 양방향에서 Person 조회 시에는 안 먹히는 이유?
    @OneToOne(fetch = FetchType.LAZY)
    private Person person;
    @OneToMany(mappedBy = "author")
    private Set<BookAuthor> books;
    // ? 유지 보수의 어려움 때문에 ManyToMany 직접 매핑은 지양한다.
    // (JoinColum을 직접 지정해야 하는데, 이 과정에서 연관 엔티티의 필드를 참조해야 할 수도 있기 때문. 즉, 유지보수가 어려워짐)
//    @ManyToMany(mappedBy = "authors")
//    private Set<Book> books;

    public Author(Person person) {
        this.person = person;
    }

    public Author() {
    }

    public Long getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }
}
