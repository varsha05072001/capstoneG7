package Shop_For_Home_C1_G7First_Microservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Shop_For_Home_C1_G7First_Microservice.entity.ProductCategory;
import Shop_For_Home_C1_G7First_Microservice.enums.ResultEnum;
import Shop_For_Home_C1_G7First_Microservice.exception.MyException;
import Shop_For_Home_C1_G7First_Microservice.repository.ProductCategoryRepository;

@Service
public class ProductCategoryService {

	@Autowired
    ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> findAll() {
        List<ProductCategory> res = productCategoryRepository.findAllByOrderByCategoryType();
        return res;
    }


    public ProductCategory findByCategoryType(Integer categoryType) {
        ProductCategory res = productCategoryRepository.findByCategoryType(categoryType);
        if(res == null) throw new MyException(ResultEnum.CATEGORY_NOT_FOUND);
        return res;
    }


    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        List<ProductCategory> res = productCategoryRepository.findByCategoryTypeInOrderByCategoryTypeAsc(categoryTypeList);
        return res;
    }

    @Transactional
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }

}
