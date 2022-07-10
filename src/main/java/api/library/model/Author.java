package api.library.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Setter;
import lombok.Getter;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Setter @Getter
@Entity
public class Author {
    @Id @GeneratedValue
    @Column(name = "author_id")
    private Long id;

    private String firstName;
    private String lastName;

    @JsonBackReference
    @OneToMany(mappedBy = "author", fetch = LAZY, cascade = ALL)
    private List<Book> books = new ArrayList<>();
}
