package lebed.ecommerce.hateos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lebed.ecommerce.model.Product;
import org.springframework.hateoas.ResourceSupport;

public class ProductResource extends ResourceSupport {

    @JsonProperty
    public Long id;
    public String name;
    public String price;
    public String description;
    public Object group;

    public ProductResource(Product model) {
        id = model.getId();
        name = model.getName();
        price = model.getPrice();
        description = model.getDescription();
        group = model.getGroup();
    }

}
