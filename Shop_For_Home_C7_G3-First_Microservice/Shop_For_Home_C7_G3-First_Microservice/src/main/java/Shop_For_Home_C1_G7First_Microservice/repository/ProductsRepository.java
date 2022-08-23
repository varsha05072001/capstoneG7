package Shop_For_Home_C1_G7First_Microservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import Shop_For_Home_C1_G7First_Microservice.entity.ProductInfo;

@Repository
public interface ProductsRepository extends JpaRepository<ProductInfo, String>{
	
	ProductInfo findByProductId(String id);
    
    Page<ProductInfo> findAllByProductStatusOrderByProductIdAsc(Integer productStatus, Pageable pageable);

    Page<ProductInfo> findAllByCategoryTypeOrderByProductIdAsc(Integer categoryType, Pageable pageable);

    Page<ProductInfo> findAllByOrderByProductId(Pageable pageable);
    
    @Query("SELECT prod FROM ProductInfo as prod ORDER BY prod.productPrice ASC")
    Page<ProductInfo> findProductsByOrderAsc(Pageable pageable);
    
    @Query("SELECT prod FROM ProductInfo as prod ORDER BY prod.productPrice DESc")
    Page<ProductInfo> findProductsByOrderDesc(Pageable pageable);

}
