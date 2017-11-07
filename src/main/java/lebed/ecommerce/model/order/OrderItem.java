package lebed.ecommerce.model.order;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lebed.ecommerce.model.GroupVariant;
import lebed.ecommerce.model.product.Product;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private GroupVariant groupVariant;
}
