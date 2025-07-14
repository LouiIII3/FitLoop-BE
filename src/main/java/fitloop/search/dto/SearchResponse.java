package fitloop.search.dto;

import fitloop.product.dto.response.ProductResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class SearchResponse {
    private List<ProductResponse> products;
//    private List<ChallengeResponse> challenges;
//    private List<LookResponse> looks;
}

