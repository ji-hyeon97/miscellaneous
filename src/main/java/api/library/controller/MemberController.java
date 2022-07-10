package api.library.controller;

import api.library.model.Member;
import api.library.model.request.MemberCreationRequest;
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
public class MemberController {
    private final LibraryService libraryService;

    @GetMapping("/member")
    @ApiOperation(value="회원 정보 조회", notes="회원 정보를 조회")
    public ResponseEntity<List<Member>> readMembers() {
        return ResponseEntity.ok(libraryService.readMembers());
    }

    @PostMapping("/member")
    @ApiOperation(value="회원 정보 입력", notes="회원 정보를 입력")
    public ResponseEntity<Member> createMember(@RequestBody MemberCreationRequest request) {
        return ResponseEntity.ok(libraryService.createMember(request));
    }

    @PatchMapping("/member/{memberId}")
    @ApiOperation(value="회원 정보 수정", notes="회원 정보를 수정")
    public ResponseEntity<Member> updateMember(@RequestBody MemberCreationRequest request, @PathVariable Long memberId) {
        return ResponseEntity.ok(libraryService.updateMember(memberId, request));
    }
}
