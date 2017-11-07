package lebed.ecommerce.service;

import lebed.ecommerce.model.order.Order;
import lebed.ecommerce.model.product.Product;
import lebed.ecommerce.model.product.ProductGroup;
import lebed.ecommerce.model.product.ProductImage;
import lebed.ecommerce.repository.GroupRepository;
import lebed.ecommerce.repository.OrderRepository;
import lebed.ecommerce.repository.ProductImageRepository;
import lebed.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ECommerceService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long id) {
        return productRepository.findOne(id);
    }

    public Product saveProduct(Product product) {
        return productRepository.saveAndFlush(product);
    }

    public ProductImage getProductImageById(String imageId) {
        return productImageRepository.findOne(Long.parseLong(imageId));
    }

    public List<ProductImage> getAllImagesByProductId(String productId) {
        return productImageRepository.findAllByProductId(Long.parseLong(productId));
    }

    public String addProductImage(final String productId, final String filename) {
        ProductImage image = new ProductImage();
        image.setProductId(Long.parseLong(productId));
        image.setPath(filename);
        return productImageRepository.saveAndFlush(image).toString();
    }

    public List<ProductGroup> getGroups() {
        return groupRepository.findAll();
    }

    public ProductGroup getGroup(Long id) {
        return groupRepository.findOne(id);
    }

    public ProductGroup saveGroup(ProductGroup group) {
        return groupRepository.saveAndFlush(group);
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(Long id) {
        return orderRepository.findOne(id);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}
