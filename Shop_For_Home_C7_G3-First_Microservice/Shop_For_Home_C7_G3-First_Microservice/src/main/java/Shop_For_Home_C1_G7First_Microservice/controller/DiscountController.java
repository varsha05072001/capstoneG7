package Shop_For_Home_C1_G7First_Microservice.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import Shop_For_Home_C1_G7First_Microservice.entity.Discount;
import Shop_For_Home_C1_G7First_Microservice.helper.ResponseMessage;


@CrossOrigin
@RestController
public class DiscountController {
	
	
	@Autowired 
	private RestTemplate restTemplate;
	 
	
	
    @PostMapping("/add/coupon/{code}")
	public ResponseEntity<ResponseMessage> createCoupon(@PathVariable("code") String code,@RequestBody List<String> emails) {
    	
    	String url="http://localhost:8081/c1g7/add/coupon/"+code;
    	List<String> requestJson=emails;
    	HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <Object> entity = new HttpEntity<Object>(requestJson,headers);
    	
       restTemplate.exchange(
    	         url, HttpMethod.POST, entity, new ParameterizedTypeReference<>() {}).getBody();
       
       String message = "Coupons Added successfully ";
    	return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));  	
    }
    
    
    
    @GetMapping("/coupon/alllist")
    public List<Discount> orderList() {
    	
    	String url="http://localhost:8081/c1g7/coupon/alllist";
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);
        
        ResponseEntity<List<Discount>> responseEntity = 
        		  restTemplate.exchange(
        		    url,
        		    HttpMethod.GET,
        		    entity,
        		    new ParameterizedTypeReference<List<Discount>>() {}
        		  );
        		List<Discount> coupons = responseEntity.getBody();  
        return coupons;
    }
    
    @PostMapping("/delete/coupon/{code}")
	public ResponseEntity<Discount> deleteCoupon(@PathVariable("code") Long code) {
    	
    	String url="http://localhost:8081/c1g7/delete/coupon/"+code;
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);
    	
        restTemplate.exchange(
    	         url, HttpMethod.POST, entity, new ParameterizedTypeReference<ResponseEntity<Discount>>() {}).getBody();
    	
    	return ResponseEntity.ok(null); 
    }



}