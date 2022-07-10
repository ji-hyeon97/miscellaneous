package api.library.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Setter @Getter
@Entity
public class Lend {
    @Id @GeneratedValue
    @Column(name = "lend_id")
    private Long id;
    private Instant startOn;
    private Instant dueOn;

    @Enumerated(EnumType.STRING)
    private LendStatus status;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @JsonManagedReference
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonManagedReference
    private Member member;
}
