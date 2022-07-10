package api.library.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;

@Getter @Setter
@Entity
public class Book {
    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;

    private String name;
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonManagedReference
    private Author author;

    @JsonBackReference
    @OneToMany(mappedBy = "book", fetch = LAZY, cascade = ALL)
    private List<Lend> lends = new ArrayList<>();
}
