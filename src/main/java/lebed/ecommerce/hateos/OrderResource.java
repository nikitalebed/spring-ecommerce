package lebed.ecommerce.hateos;

import com.fasterxml.jackson.annotation.JsonProperty;

import lebed.ecommerce.model.order.Order;
import lebed.ecommerce.model.order.OrderItem;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class OrderResource extends ResourceSupport {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String address;

    @JsonProperty
    private String city;

    @JsonProperty
    private String zip;

    @JsonProperty
    private String status;

    @JsonProperty
    private String comment;

    @JsonProperty
    private String totalPrice;

    @JsonProperty
    private String type;

    @JsonProperty
    private String created;

    @JsonProperty
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

