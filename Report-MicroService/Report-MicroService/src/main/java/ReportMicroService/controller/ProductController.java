package ReportMicroService.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ReportMicroService.response.ProductInfoResponse;
import ReportMicroService.service.ProductService;

@RestController
@CrossOrigin
public class ProductController {
	
	@Autowired
    ProductService productService;

    @GetMapping("/productall")
    public ProductInfoResponse findAll() {
    	ProductInfoResponse response=new ProductInfoResponse();
    	response.setProductList(productService.findAll());
        return response;
    }
}
