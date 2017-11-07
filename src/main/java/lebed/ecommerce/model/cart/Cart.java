package lebed.ecommerce.model.cart;

import lombok.Data;

import java.util.List;

@Data
public class Cart {
    private List<CartItem> items;
}
