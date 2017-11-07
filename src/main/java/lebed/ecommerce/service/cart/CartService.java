package lebed.ecommerce.service.cart;

import lebed.ecommerce.model.cart.CartItem;
import lebed.ecommerce.model.order.Order;

import java.util.Set;

public interface CartService {

    String createNewCart();

    void addProduct(String cartId, CartItem cartItem);

    void removeProduct(String cartId, String productId);

    void setProductQuantity(String cartId, String productId, Long quantity);

    Set<CartItem> getItems(String cartId);

    Order createOrder(String cartId, Order order);
}
