package Shop_For_Home_C1_G7First_Microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Shop_For_Home_C1_G7First_Microservice.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

}
