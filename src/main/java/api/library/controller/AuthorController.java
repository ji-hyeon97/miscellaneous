package api.library.controller;

import api.library.model.Author;
import api.library.model.request.AuthorCreationRequest;
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
public class AuthorController {

    private final LibraryService libraryService;

    @GetMapping("/author")
    @ApiOperation(value="저자 정보 조회", notes="저자 정보를 조회")
    public ResponseEntity<List<Author>> readAuthors() {
        return ResponseEntity.ok(libraryService.readAuthors());
    }

    @PostMapping("/author")
    @ApiOperation(value="저자 정보 입력", notes="저자 정보 입력")
    public ResponseEntity<Author> createAuthor(@RequestBody AuthorCreationRequest request) {
        return ResponseEntity.ok(libraryService.createAuthor(request));
    }
}
