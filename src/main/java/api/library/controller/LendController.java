package api.library.controller;

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
public class LendController {
    private final LibraryService libraryService;

    @PostMapping("/book/lend")
    @ApiOperation(value="책 대출하기", notes="책 대출하기")
    public ResponseEntity<List<String>> lendABook(@RequestBody BookLendRequest bookLendRequests) {
        return ResponseEntity.ok(libraryService.lendBook(bookLendRequests));
    }
}
