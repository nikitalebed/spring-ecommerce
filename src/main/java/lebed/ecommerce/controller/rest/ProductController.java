package lebed.ecommerce.controller.rest;

import lebed.ecommerce.hateos.ProductResource;
import lebed.ecommerce.model.product.Product;
import lebed.ecommerce.model.product.ProductImage;
import lebed.ecommerce.service.ECommerceService;
import lebed.ecommerce.service.files.StorageService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController implements CoreController {

    private static final String PRODUCT_IMAGES_FOLDER = "product-images/";
    private static final String IMAGE_PNG_MIME_TYPE = "image/png";

    @Autowired
    private ECommerceService ecommerceService;

    @Autowired
    private StorageService storageService;

    @GetMapping
    public List<ProductResource> index() {
        return ecommerceService.getProducts().stream().map(product -> {
            ProductResource productResource = new ProductResource(product);
            productResource.add(createHateoasLink(product.getId()));
            return productResource;
        }).collect(Collectors.toList());
    }

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

    @GetMapping("/{id}/images")
    public List<ProductImage> viewImages(@PathVariable("id") @NonNull String productId) {
        return ecommerceService.getAllImagesByProductId(productId);
    }

    @GetMapping("/image/{id}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable("id") String id) throws IOException {
        ProductImage image = ecommerceService.getProductImageById(id);
        String path = PRODUCT_IMAGES_FOLDER + image.getProductId() + "/";
        Resource file = storageService.loadAsResource(path + image.getPath());
        String mimeType = file.getURL().openConnection().getContentType();
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, mimeType).body(file);
    }

    @PostMapping("/{id}/uploadimage")
    public String handleFileUpload(@PathVariable("id") String id, @RequestParam("file") MultipartFile file) {

        // Relative path to the rootLocation in storageService
        String path = "/product-images/" + id;
        String filename = storageService.store(file, path);

        return ecommerceService.addProductImage(id, filename);
    }

    private boolean checkProductNotNull(Product updatedProduct) {
        return updatedProduct == null;
    }
}
