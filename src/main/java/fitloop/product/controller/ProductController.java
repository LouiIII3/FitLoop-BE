package fitloop.product.controller;

import fitloop.member.auth.Login;
import fitloop.member.auth.MemberIdentity;
import fitloop.member.auth.VerifiedMember;
import fitloop.product.dto.request.ProductRegisterRequest;
import fitloop.product.dto.response.ProductDetailResponse;
import fitloop.product.dto.response.ProductResponse;
import fitloop.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Login
    @PostMapping("/register")
    public ResponseEntity<?> createProduct(
            @RequestBody @Valid ProductRegisterRequest productRegisterRequest,
            @VerifiedMember MemberIdentity member,
            @RequestHeader("access") String accessToken
    ) {
        return productService.createProduct(productRegisterRequest, member, accessToken);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<ProductResponse>> getRecentProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @VerifiedMember MemberIdentity member
    ) {
        Long userId = member != null ? member.id() : null;
        return ResponseEntity.ok(productService.getRecentProducts(page, size, userId));
    }

    @GetMapping("/popular")
    public ResponseEntity<List<ProductResponse>> getPopularProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @VerifiedMember MemberIdentity member
    ) {
        Long userId = member != null ? member.id() : null;
        return ResponseEntity.ok(productService.getPopularProducts(page, size, userId));
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductResponse>> getCategoryProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            @RequestParam int categoryCode,
            @RequestParam String gender,
            @VerifiedMember MemberIdentity member
    ) {
        Long userId = member != null ? member.id() : null;
        return ResponseEntity.ok(productService.getCategoryProducts(page, size, categoryCode, gender, userId));
    }

    @GetMapping("/{id}")
    public ProductDetailResponse getProductDetail(
            @PathVariable Long id,
            @VerifiedMember MemberIdentity member
    ) {
        Long userId = member != null ? member.id() : null;
        return productService.getProductDetail(id, userId);
    }
}
