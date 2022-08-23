package ReportMicroService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ReportMicroService.entity.ProductInfo;
import ReportMicroService.repository.ProductInfoRepository;

@Service
public class ProductService {
	
	@Autowired
    ProductInfoRepository productInfoRepository;

	@Transactional
	public List<ProductInfo> findAll() {
		
		return productInfoRepository.findAll();
	}

}
