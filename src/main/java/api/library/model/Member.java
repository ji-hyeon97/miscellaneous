package api.library.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Getter @Setter
@Entity
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @JsonBackReference
    @OneToMany(mappedBy = "member", fetch = LAZY, cascade = ALL)
    private List<Lend> lends = new ArrayList<>();
}
