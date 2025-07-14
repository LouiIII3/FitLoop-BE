package fitloop.search.service;

import fitloop.product.dto.response.ProductResponse;
import fitloop.product.entity.ProductEntity;
import fitloop.product.repository.ProductImageRepository;
import fitloop.product.repository.ProductRepository;
import fitloop.product.repository.ProductTagRepository;
import fitloop.search.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final ProductRepository productRepository;
    private final ProductTagRepository productTagRepository;
    private final ProductImageRepository productImageRepository;

    public SearchResponse searchAll(String query) {
        if (query == null || query.trim().length() < 2) {
            return SearchResponse.builder()
                    .products(List.of())
                    .build();
        }

        List<ProductEntity> products = productRepository.searchByQuery(query.trim());

        List<ProductResponse> productResponses = products.stream().map(product -> {
            List<String> tags = productTagRepository.findByProductEntityId(product.getId())
                    .stream()
                    .map(tag -> tag.getTagName())
                    .collect(Collectors.toList());

            List<String> imageUrls = productImageRepository.findByProductEntityId(product.getId())
                    .stream()
                    .map(image -> image.getImageURL())
                    .collect(Collectors.toList());

            return ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .isFree(product.isFree())
                    .includeShipping(product.isIncludeShipping())
                    .createdAt(product.getCreatedAt())
                    .likeCount(product.getLikeCount())
                    .tags(tags)
                    .imageUrls(imageUrls)
                    .build();
        }).toList();

        return SearchResponse.builder()
                .products(productResponses)
                .build();
    }
}