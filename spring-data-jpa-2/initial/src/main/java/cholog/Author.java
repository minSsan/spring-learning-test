package cholog;

import jakarta.persistence.*;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
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
