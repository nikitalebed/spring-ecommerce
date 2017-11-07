package lebed.ecommerce.hateos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lebed.ecommerce.model.Order;
import lebed.ecommerce.model.OrderItem;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class OrderResource extends ResourceSupport {

    @JsonProperty
    private Long id;
    private String name;
    private String address;
    private String city;
    private String zip;
    private String status;
    private String comment;
    private String totalPrice;
    private String type;
    private String created;
    private List<OrderItem> items;

    public OrderResource(Order order) {
        id = order.getId();
        name = order.getName();
        address = order.getAddress();
        city = order.getCity();
        zip = order.getZip();
        status = order.getStatus();
        comment = order.getComment();
        totalPrice = order.getTotalPrice();
        type = order.getType();
        created = order.getCreated();
        items = order.getItems();
    }
}

