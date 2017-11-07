package lebed.ecommerce.hateos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lebed.ecommerce.model.GroupVariant;
import lebed.ecommerce.model.ProductGroup;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class GroupResource extends ResourceSupport {

    @JsonProperty
    private Long id;
    private String groupName;
    private String price;
    private List<GroupVariant> variants;

    public GroupResource(ProductGroup group) {
        this.id = group.getId();
        this.groupName = group.getGroupName();
        this.price = group.getPrice();
        this.variants = group.getGroupVariants();
    }
}
