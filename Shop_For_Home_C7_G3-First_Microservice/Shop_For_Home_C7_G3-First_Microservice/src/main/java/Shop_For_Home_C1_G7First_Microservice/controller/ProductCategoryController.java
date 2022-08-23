package Shop_For_Home_C1_G7First_Microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import Shop_For_Home_C1_G7First_Microservice.entity.ProductCategory;
import Shop_For_Home_C1_G7First_Microservice.entity.ProductInfo;
import Shop_For_Home_C1_G7First_Microservice.response.CategoryPage;
import Shop_For_Home_C1_G7First_Microservice.service.ProductCategoryService;
import Shop_For_Home_C1_G7First_Microservice.service.ProductService;

@RestController
@CrossOrigin
public class ProductCategoryController {
	
	@Autowired
    ProductCategoryService ProductCategoryService;
	
    @Autowired
    ProductService productService;

    @GetMapping("/category/{type}")
    public CategoryPage showOne(@PathVariable("type") Integer categoryType,
                                @RequestParam(value = "page", defaultValue = "1") Integer page,
                                @RequestParam(value = "size", defaultValue = "3") Integer size) {

        ProductCategory cat = ProductCategoryService.findByCategoryType(categoryType);
        PageRequest request = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInCategory = productService.findAllInCategory(categoryType, request);
        var tmp = new CategoryPage("", productInCategory);
        tmp.setCategory(cat.getCategoryName());
        return tmp;
    }
}
