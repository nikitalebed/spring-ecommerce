package lebed.ecommerce.model.cart;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CartItem {

    private Long productId;
    private Long variantId;
    private Long quantity;

}
