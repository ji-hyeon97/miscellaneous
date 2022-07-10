package api.library.service;

import api.library.model.*;
import api.library.model.request.AuthorCreationRequest;
import api.library.model.request.BookCreationRequest;
import api.library.model.request.BookLendRequest;
import api.library.model.request.MemberCreationRequest;
import api.library.repository.AuthorRepository;
import api.library.repository.BookRepository;
import api.library.repository.LendRepository;
import api.library.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final MemberRepository memberRepository;
    private final LendRepository lendRepository;
    private final BookRepository bookRepository;

    public Book findBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Cant find any book under given ID");
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBook(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        if (book.isPresent()) {
            return book.get();
        }
        throw new EntityNotFoundException("Can't find any book under given ISBN");
    }

    public Book createBook(BookCreationRequest book) {
        Optional<Author> author = authorRepository.findById(book.getAuthorId());
        if (author.isEmpty()) {
            throw new EntityNotFoundException("Author Not Found");
        }
        Book bookToCreate = new Book();
        BeanUtils.copyProperties(book, bookToCreate);
        bookToCreate.setAuthor(author.get());
        return bookRepository.save(bookToCreate);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Member createMember(MemberCreationRequest request) {
        Member member = new Member();
        BeanUtils.copyProperties(request, member);
        member.setStatus(MemberStatus.ACTIVE);
        return memberRepository.save(member);
    }

    public Member updateMember (Long id, MemberCreationRequest request) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isEmpty()) {
            throw new EntityNotFoundException("Member not present in the database");
        }
        Member member = optionalMember.get();
        member.setLastName(request.getLastName());
        member.setFirstName(request.getFirstName());
        return memberRepository.save(member);
    }

    public Author createAuthor (AuthorCreationRequest request) {
        Author author = new Author();
        BeanUtils.copyProperties(request, author);
        return authorRepository.save(author);
    }

    public List<String> lendBook (BookLendRequest request) {

        Optional<Member> memberForId = memberRepository.findById(request.getMemberId());
        if (memberForId.isEmpty()) {
            throw new EntityNotFoundException("Member not present in the database");
        }

        Member member = memberForId.get();
        if (member.getStatus() != MemberStatus.ACTIVE) {
            throw new RuntimeException("User is not active to proceed a lending.");
        }

        List<String> booksApprovedToBorrow = new ArrayList<>();

        request.getBookIds().stream().map(bookRepository::findById).forEach(bookForId -> {
            if (bookForId.isEmpty()) {
                throw new EntityNotFoundException("Cant find any book under given ID");
            } else {
                Optional<Lend> borrowedBook = lendRepository.findByBookAndStatus(bookForId.get(), LendStatus.BORROWED);
                if (borrowedBook.isEmpty()) {
                    booksApprovedToBorrow.add(bookForId.get().getName());
                    Lend lend = new Lend();
                    lend.setMember(memberForId.get());
                    lend.setBook(bookForId.get());
                    lend.setStatus(LendStatus.BORROWED);
                    lend.setStartOn(Instant.now());
                    lend.setDueOn(Instant.now().plus(30, ChronoUnit.DAYS));
                    lendRepository.save(lend);
                }
            }
        });
        return booksApprovedToBorrow;
    }

    public List<Author> readAuthors() {
        return authorRepository.findAll();
    }

    public Book updateBook(Long bookId, BookCreationRequest request) {
        Optional<Author> author = authorRepository.findById(request.getAuthorId());
        if (author.isEmpty()) {
            throw new EntityNotFoundException("Author Not Found");
        }
        Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isEmpty()) {
            throw new EntityNotFoundException("Book Not Found");
        }
        Book book = optionalBook.get();
        book.setIsbn(request.getIsbn());
        book.setName(request.getName());
        book.setAuthor(author.get());
        return bookRepository.save(book);
    }

    public List<Member> readMembers() {
        return memberRepository.findAll();
    }
}
