package lebed.ecommerce.controller.rest;

import lebed.ecommerce.hateos.OrderResource;
import lebed.ecommerce.model.cart.CartItem;
import lebed.ecommerce.model.order.Order;
import lebed.ecommerce.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/cart")
public class CartController implements CoreController {

    @Autowired
    private CartService cartService;

    @PostMapping("/")
    public String create() {
        return cartService.createNewCart();
    }

    @PostMapping("/{id}")
    public String addProduct(@PathVariable("id") String cartId, @RequestBody CartItem cartItem) {
        cartService.addProduct(cartId, cartItem);
        return "OK";
    }

    @GetMapping("/{id}")
    public Set<CartItem> getCartItems(@PathVariable("id") String cartId) {
        return cartService.getItems(cartId);
    }

    @DeleteMapping("{id}/{product_id}")
    public String removeItem(@PathVariable("id") String cartId, @PathVariable("product_id") String productId) {
        cartService.removeProduct(cartId, productId);
        return "OK";
    }

    @PostMapping("{id}/quantity")
    public String setProductQuantity(@PathVariable("id") String cartId, @RequestBody CartItem cartItem) {
        String productId = Long.toString(cartItem.getProductId());
        cartService.setProductQuantity(cartId, productId, cartItem.getQuantity());
        return "OK";
    }

    @PostMapping("{id}/order")
    public OrderResource createOrder(@PathVariable("id") String cartId, @RequestBody @Valid Order order) {
        if (order == null) {
            System.out.println("Order not in POST");
            return null;
        }
        OrderResource orderResource = new OrderResource(cartService.createOrder(cartId, order));
        Link link = linkTo(OrderController.class).slash(order.getId()).withSelfRel();
        orderResource.add(link);
        if (order.getId() < 1) {
            System.out.println("Resource not created");
            return null;
        }
        return orderResource;

    }
}
