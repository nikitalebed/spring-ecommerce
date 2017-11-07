package lebed.ecommerce.repository;

import lebed.ecommerce.model.product.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<ProductGroup, Long> {

}
