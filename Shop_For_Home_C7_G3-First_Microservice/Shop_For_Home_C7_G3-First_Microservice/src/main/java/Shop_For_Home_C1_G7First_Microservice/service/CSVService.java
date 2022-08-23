package Shop_For_Home_C1_G7First_Microservice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Shop_For_Home_C1_G7First_Microservice.entity.ProductInfo;
import Shop_For_Home_C1_G7First_Microservice.helper.CSVHelper;
import Shop_For_Home_C1_G7First_Microservice.repository.ProductsRepository;


@Service
public class CSVService {
	
	@Autowired
	ProductsRepository productsrepository;
	
	public void save(MultipartFile file) {
	    try {
	      List<ProductInfo> tutorials = CSVHelper.csvToTutorials(file.getInputStream());
	      productsrepository.saveAll(tutorials);
	    } catch (IOException e) {
	      throw new RuntimeException("fail to store csv data: " + e.getMessage());
	    }
	  }
	
	  public List<ProductInfo> getAllTutorials() {
	    return productsrepository.findAll();
	  }

}
