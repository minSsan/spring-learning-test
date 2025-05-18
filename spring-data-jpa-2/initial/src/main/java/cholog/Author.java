package cholog;

import jakarta.persistence.*;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 단방향에서는 LAZY 로딩이 먹히는데, 양방향에서 Person 조회 시에는 안 먹히는 이유?
    @OneToOne(fetch = FetchType.LAZY)
    private Person person;

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
