package Shop_For_Home_C1_G7First_Microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Shop_For_Home_C1_G7First_Microservice.entity.ProductInOrder;
import Shop_For_Home_C1_G7First_Microservice.entity.User;
import Shop_For_Home_C1_G7First_Microservice.repository.ProductInOrderRepository;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductInOrderService {
	
	@Autowired
    ProductInOrderRepository productInOrderRepository;

    @Transactional
    public void update(String itemId, Integer quantity, User user) {
        var op = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
        op.ifPresent(productInOrder -> {
            productInOrder.setCount(quantity);
            productInOrderRepository.save(productInOrder);
        });

    }

    public ProductInOrder findOne(String itemId, User user) {
        var op = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
        AtomicReference<ProductInOrder> res = new AtomicReference<>();
        op.ifPresent(res::set);
        return res.get();
    }
}
