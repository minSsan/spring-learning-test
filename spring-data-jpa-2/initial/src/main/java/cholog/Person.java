package cholog;

import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // 연관 관계의 주인이 아니므로, person 은 pk로 author 를 관리하지 않는다.
    // ? fetch type을 LAZY로 설정해도 Join이 실행되는 이유?
    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private Author author;

    public Person() {

    }

    public Person(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Author getAuthor() {
        return author;
    }
}
