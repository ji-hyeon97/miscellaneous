package api.library.controller;

import api.library.model.Book;
import api.library.model.request.BookCreationRequest;
import api.library.model.request.BookLendRequest;
import api.library.service.LibraryService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/library")
@CrossOrigin("*")
public class BookController {

    private final LibraryService libraryService;

    @GetMapping("/book")
    @ApiOperation(value="모든 책 정보 조회", notes="모든 책 정보를 조회(단, isbn 을 통해서 찾을 수 있음)")
    public ResponseEntity readBooks(@RequestParam(required = false) String isbn) {
        if (isbn == null) {
            return ResponseEntity.ok(libraryService.findAllBooks());
        }
        return ResponseEntity.ok(libraryService.findBook(isbn));
    }

    @PostMapping("/book")
    @ApiOperation(value="책 정보 입력", notes="책 정보 입력")
    public ResponseEntity<Book> createBook(@RequestBody BookCreationRequest request) {
        return ResponseEntity.ok(libraryService.createBook(request));
    }

    @GetMapping("/book/{bookId}")
    @ApiOperation(value="책 정보 조회", notes="책 정보 조회")
    public ResponseEntity<Book> readBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(libraryService.findBook(bookId));
    }

    @PatchMapping("/book/{bookId}")
    @ApiOperation(value="책 정보 수정", notes="책 정보 수정")
    public ResponseEntity<Book> updateBook(@PathVariable("bookId") Long bookId, @RequestBody BookCreationRequest request) {
        return ResponseEntity.ok(libraryService.updateBook(bookId, request));
    }

    @DeleteMapping("/book/{bookId}")
    @ApiOperation(value="책 정보 삭제", notes="책 정보 삭제")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        libraryService.deleteBook(bookId);
        return ResponseEntity.ok().build();
    }
}
