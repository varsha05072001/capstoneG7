package Shop_For_Home_C1_G7First_Microservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Shop_For_Home_C1_G7First_Microservice.entity.ProductInfo;
import Shop_For_Home_C1_G7First_Microservice.enums.ProductStatusEnum;
import Shop_For_Home_C1_G7First_Microservice.enums.ResultEnum;
import Shop_For_Home_C1_G7First_Microservice.exception.MyException;
import Shop_For_Home_C1_G7First_Microservice.repository.ProductsRepository;

@Service
public class ProductService {
	
	@Autowired
    ProductsRepository productsRepository;

    @Autowired
    ProductCategoryService productcategoryService;

    public ProductInfo findOne(String productId) {

        ProductInfo productInfo = productsRepository.findByProductId(productId);
        return productInfo;
    }


    public Page<ProductInfo> findUpAll(Pageable pageable) {
        return productsRepository.findAllByProductStatusOrderByProductIdAsc(ProductStatusEnum.UP.getCode(),pageable);
    }

    public Page<ProductInfo> findAll(Pageable pageable) {
        return productsRepository.findAllByOrderByProductId(pageable);
    }

    public Page<ProductInfo> findAllInCategory(Integer categoryType, Pageable pageable) {
        return productsRepository.findAllByCategoryTypeOrderByProductIdAsc(categoryType, pageable);
    }

    @Transactional
    public void increaseStock(String productId, int amount) {
        ProductInfo products = findOne(productId);
        if (products == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        int update = products.getProductStock() + amount;
        products.setProductStock(update);
        productsRepository.save(products);
    }

    @Transactional
    public void decreaseStock(String productId, int amount) {
        ProductInfo productInfo = findOne(productId);
        if (productInfo == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        int update = productInfo.getProductStock() - amount;
        if(update <= 0) throw new MyException(ResultEnum.PRODUCT_NOT_ENOUGH );

        productInfo.setProductStock(update);
        productsRepository.save(productInfo);
    }

    @Transactional
    public ProductInfo offSale(String productId) {
        ProductInfo products = findOne(productId);
        if (products == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        if (products.getProductStatus() == ProductStatusEnum.DOWN.getCode()) {
            throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        products.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return productsRepository.save(products);
    }

    @Transactional
    public ProductInfo onSale(String productId) {
        ProductInfo products = findOne(productId);
        if (products == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);

        if (products.getProductStatus() == ProductStatusEnum.UP.getCode()) {
            throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        products.setProductStatus(ProductStatusEnum.UP.getCode());
        return productsRepository.save(products);
    }

    public ProductInfo update(ProductInfo productInfo) {
        productcategoryService.findByCategoryType(productInfo.getCategoryType());
        if(productInfo.getProductStatus() > 1) {
            throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        return productsRepository.save(productInfo);
    }


    public ProductInfo save(ProductInfo productInfo) {
        return update(productInfo);
    }

    public void delete(String productId) {
        ProductInfo products = findOne(productId);
        if (products == null) throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);
        productsRepository.delete(products);

    }

	@Transactional
	public List<ProductInfo> findAll() {
		
		return productsRepository.findAll();
	}
	
	@Transactional
	public Page<ProductInfo> findProductsByAsc(Pageable pageable) {
        return productsRepository.findProductsByOrderAsc(pageable);
    }
	
	@Transactional
	public Page<ProductInfo> findProductsByDesc(Pageable pageable) {
        return productsRepository.findProductsByOrderDesc(pageable);
    }

}
