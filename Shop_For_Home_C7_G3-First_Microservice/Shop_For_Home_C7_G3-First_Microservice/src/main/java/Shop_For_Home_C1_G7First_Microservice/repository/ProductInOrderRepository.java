package Shop_For_Home_C1_G7First_Microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Shop_For_Home_C1_G7First_Microservice.entity.ProductInOrder;

@Repository
public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long>{

}
