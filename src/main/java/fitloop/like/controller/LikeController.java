package fitloop.like.controller;

import fitloop.member.auth.Login;
import fitloop.member.auth.MemberIdentity;
import fitloop.member.auth.VerifiedMember;
import fitloop.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products/{productId}/like")
public class LikeController {

    private final LikeService likeService;

    // 좋아요 추가
    @Login
    @PostMapping
    public ResponseEntity<?> likeProduct(
            @PathVariable Long productId,
            @VerifiedMember MemberIdentity member
    ) {
        likeService.likeProduct(member.id(), productId);
        return ResponseEntity.ok().body("좋아요 추가 완료");
    }

    // 좋아요 취소
    @Login
    @DeleteMapping
    public ResponseEntity<?> unlikeProduct(
            @PathVariable Long productId,
            @VerifiedMember MemberIdentity member
    ) {
        likeService.unlikeProduct(member.id(), productId);
        return ResponseEntity.ok().body("좋아요 취소 완료");
    }
}
