package lebed.ecommerce.service.cart;

import lebed.ecommerce.model.GroupVariant;
import lebed.ecommerce.model.cart.CartItem;
import lebed.ecommerce.model.order.Order;
import lebed.ecommerce.model.order.OrderItem;
import lebed.ecommerce.model.product.Product;
import lebed.ecommerce.service.ECommerceService;
import lebed.ecommerce.service.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ECommerceService eCommerceService;

    @Autowired
    private Cache cache;

    @Override
    public String createNewCart() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void addProduct(String cartId, CartItem cartItem) {
        cache.addItemToList(cartId, cartItem);
    }

    @Override
    public void removeProduct(String cartId, String productId) {
        CartItem itemToRemove = new CartItem();
        itemToRemove.setProductId(Long.parseLong(productId));
        cache.removeItemFromList(cartId, itemToRemove);
    }

    @Override
    public void setProductQuantity(String cartId, String productId, Long quantity) {
        List<CartItem> list = cache.getList(cartId);
        final long parsedProductId = Long.parseLong(productId);
        list.forEach(cartItem -> {
            try {
                if (cartItem.getProductId() == parsedProductId) {
                    cartItem.setQuantity(quantity);
                    cache.removeItemFromList(cartId, cartItem);
                    cache.addItemToList(cartId, cartItem);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
    }

    @Override
    public Set<CartItem> getItems(String cartId) {
        return new HashSet<>(cache.getList(cartId));
    }

    @Override
    public Order createOrder(String cartId, Order order) {
        List<CartItem> cartItems = cache.getList(cartId);
        order = addCartItemsToOrders(cartItems, order);
        if (order == null) {
            System.out.println("Order not set.");
        }
        order = eCommerceService.saveOrder(order);
        cache.removeItem(cartId);
        return order;
    }

    private Order addCartItemsToOrders(List<CartItem> cartItems, Order order) {
        cartItems.forEach(cartItem -> {
            Product prod = eCommerceService.getProduct(cartItem.getProductId());
            Long qty = cartItem.getQuantity() > 0 ? cartItem.getQuantity() : 1;
            Long variantId = cartItem.getVariantId();

            for (int i = 0; i < qty; i++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(prod);
                if (variantId > 0) {
                    GroupVariant v = new GroupVariant();
                    v.setId(variantId);
                    orderItem.setGroupVariant(v);
                }
                orderItem.setOrder(order);
                order.getItems().add(orderItem);
            }

        });
        return order;
    }
}
