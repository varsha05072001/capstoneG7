package Shop_For_Home_C1_G7First_Microservice.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Shop_For_Home_C1_G7First_Microservice.entity.ProductInfo;
import Shop_For_Home_C1_G7First_Microservice.response.ProductsResponse;
import Shop_For_Home_C1_G7First_Microservice.service.ProductCategoryService;
import Shop_For_Home_C1_G7First_Microservice.service.ProductService;



@CrossOrigin
@RestController
public class ProductController {
	
    @Autowired
    ProductCategoryService productCategoryService;
    
    @Autowired
    ProductService productService;

   
    @GetMapping("/product")
    public Page<ProductInfo> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.findAll(request);
    }
    
    @GetMapping("/productbyasc")
    public Page<ProductInfo> findProductsByAsc(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.findProductsByAsc(request);
    }
    
    @GetMapping("/productbydesc")
    public Page<ProductInfo> findProductsByDesc(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        PageRequest request = PageRequest.of(page - 1, size);
        return productService.findProductsByDesc(request);
    }
    
    @GetMapping("/productall")
    public ProductsResponse findAll() {
    	ProductsResponse response=new ProductsResponse();
    	response.setProductList(productService.findAll());
        return response;
    }

    @GetMapping("/product/{productId}")
    public ProductInfo showOne(@PathVariable("productId") String productId) {

        ProductInfo productInfo = productService.findOne(productId);
        return productInfo;
    }

    @PostMapping("/seller/product/new")
    public ResponseEntity create(@Valid @RequestBody ProductInfo product,
                                 BindingResult bindingResult) {
        ProductInfo productIdExists = productService.findOne(product.getProductId());
        if (productIdExists != null) {
            bindingResult
                    .rejectValue("productId", "error.product",
                            "There is already a product with the code provided");
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        return ResponseEntity.ok(productService.save(product));
    }

    @PutMapping("/seller/product/{id}/edit")
    public ResponseEntity edit(@PathVariable("id") String productId,
                               @Valid @RequestBody ProductInfo product,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }
        if (!productId.equals(product.getProductId())) {
            return ResponseEntity.badRequest().body("Id Not Matched");
        }

        return ResponseEntity.ok(productService.update(product));
    }

    @DeleteMapping("/seller/product/{id}/delete")
    public ResponseEntity delete(@PathVariable("id") String productId) {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }

}