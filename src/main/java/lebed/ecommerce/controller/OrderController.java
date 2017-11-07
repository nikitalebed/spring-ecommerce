package lebed.ecommerce.controller;

import lebed.ecommerce.hateos.OrderResource;
import lebed.ecommerce.model.Order;
import lebed.ecommerce.service.ECommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class OrderController implements CoreController {

    @Autowired
    private ECommerceService eCommerceService;

    @RequestMapping(method = RequestMethod.GET)
    public List<OrderResource> index() {
        return eCommerceService.getOrders().stream().map(order -> {
            OrderResource orderResource = new OrderResource(order);
            orderResource.add(createHateoasLink(order.getId()));
            return orderResource;
        }).collect(Collectors.toList());
    }

    @PostMapping
    public Order create(@RequestBody @Valid Order order) {
        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }
        return eCommerceService.saveOrder(order);
    }

    @RequestMapping("/{id}")
    public OrderResource view(@PathVariable("id") Long id) {
        OrderResource orderResource = new OrderResource(eCommerceService.getOrder(id));
        orderResource.add(createHateoasLink(id));
        return orderResource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public Order edit(@PathVariable("id") Long id, @RequestBody @Valid Order order) {
        Order updatedOrder = eCommerceService.getOrder(id);
        if (updatedOrder == null) {
            return null;
        }
        return eCommerceService.saveOrder(order);
    }
}
