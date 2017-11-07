package lebed.ecommerce.hateos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lebed.ecommerce.model.Product;
import org.springframework.hateoas.ResourceSupport;

public class ProductResource extends ResourceSupport {

    @JsonProperty
    private Long id;
    private String name;
    private String price;
    private String description;
    private Object group;

    public ProductResource(Product product) {
        id = product.getId();
        name = product.getName();
        price = product.getPrice();
        description = product.getDescription();
        group = product.getGroup();
    }

}
