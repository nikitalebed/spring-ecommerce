package lebed.ecommerce.controller;

import lebed.ecommerce.hateos.ProductResource;
import lebed.ecommerce.model.Product;
import lebed.ecommerce.service.ECommerceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController implements CoreController {

    @Autowired
    private ECommerceService ecommerceService;

    @PostMapping
    public Product create(@RequestBody @Valid Product product) {
        return ecommerceService.saveProduct(product);
    }

    @GetMapping("/{id}")
    public ResourceSupport getById(@PathVariable("id") Long id) {
        Product product = ecommerceService.getProduct(id);
        if (checkProductNotNull(product)) return null;
        ProductResource productResource = new ProductResource(product);
        productResource.add(createHateoasLink(product.getId()));

        return productResource;
    }

    @PostMapping(value = "/{id}")
    public Product edit(@PathVariable("id") Long id, @RequestBody @Valid Product product) {
        Product updatedProduct = ecommerceService.getProduct(id);
        if (checkProductNotNull(updatedProduct)) return null;
        updatedProduct.setName(product.getName());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setDescription(product.getDescription());

        return ecommerceService.saveProduct(updatedProduct);
    }

    private boolean checkProductNotNull(Product updatedProduct) {
        return updatedProduct == null;
    }
}
