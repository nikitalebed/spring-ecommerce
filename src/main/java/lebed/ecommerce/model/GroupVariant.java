package lebed.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lebed.ecommerce.model.product.ProductGroup;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "group_variants")
public class GroupVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "variant_name")
    private String variantName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    @JsonBackReference
    @JsonIgnore
    private ProductGroup group;

}
