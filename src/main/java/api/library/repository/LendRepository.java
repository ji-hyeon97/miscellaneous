package api.library.repository;

import api.library.model.Book;
import api.library.model.Lend;
import api.library.model.LendStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LendRepository extends JpaRepository<Lend, Long> {
    Optional<Lend> findByBookAndStatus(Book book, LendStatus status);
}
