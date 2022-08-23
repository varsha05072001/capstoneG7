package Shop_For_Home_C1_G7First_Microservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Shop_For_Home_C1_G7First_Microservice.entity.OrderMain;
import Shop_For_Home_C1_G7First_Microservice.entity.ProductInOrder;
import Shop_For_Home_C1_G7First_Microservice.entity.ProductInfo;
import Shop_For_Home_C1_G7First_Microservice.enums.OrderStatusEnum;
import Shop_For_Home_C1_G7First_Microservice.enums.ResultEnum;
import Shop_For_Home_C1_G7First_Microservice.exception.MyException;
import Shop_For_Home_C1_G7First_Microservice.repository.OrderRepository;
import Shop_For_Home_C1_G7First_Microservice.repository.ProductInOrderRepository;
import Shop_For_Home_C1_G7First_Microservice.repository.ProductsRepository;
import Shop_For_Home_C1_G7First_Microservice.repository.UsersRepository;

@Service
public class OrderService {
	@Autowired
    OrderRepository orderRepository;
    @Autowired
    UsersRepository userRepository;
    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    ProductService productService;
    @Autowired
    ProductInOrderRepository productInOrderRepository;

    
    public Page<OrderMain> findAll(Pageable pageable) {
        return orderRepository.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
    }

    
    public Page<OrderMain> findByStatus(Integer status, Pageable pageable) {
        return orderRepository.findAllByOrderStatusOrderByCreateTimeDesc(status, pageable);
    }

    
    public Page<OrderMain> findByBuyerEmail(String email, Pageable pageable) {
        return orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
    }

    
    public Page<OrderMain> findByBuyerPhone(String phone, Pageable pageable) {
        return orderRepository.findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(phone, pageable);
    }

    
    public OrderMain findOne(Long orderId) {
        OrderMain orderMain = orderRepository.findByOrderId(orderId);
        if(orderMain == null) {
            throw new MyException(ResultEnum.ORDER_NOT_FOUND);
        }
        return orderMain;
    }

    @Transactional
    public OrderMain finish(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        if(!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        orderRepository.save(orderMain);
        return orderRepository.findByOrderId(orderId);
    }

    
    @Transactional
    public OrderMain cancel(Long orderId) {
        OrderMain orderMain = findOne(orderId);
        if(!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
        }

        orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getCode());
        orderRepository.save(orderMain);

        // Restore Stock
        Iterable<ProductInOrder> products = orderMain.getProducts();
        for(ProductInOrder productInOrder : products) {
            ProductInfo productDetails = productsRepository.findByProductId(productInOrder.getProductId());
            if(productDetails != null) {
                productService.increaseStock(productInOrder.getProductId(), productInOrder.getCount());
            }
        }
        return orderRepository.findByOrderId(orderId);

    }
}