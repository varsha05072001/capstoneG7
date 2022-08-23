package DiscountMicroService.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DiscountMicroService.entity.Discount;
import DiscountMicroService.repository.DiscountRepository;

@Service
public class DiscountService {
	
	@Autowired
	DiscountRepository discountRepository;
	
	@Transactional
	public Discount createCoupon(String code) {
		Integer status=0;
		Discount coupon=new Discount();
		coupon.setCoupon(code);
		coupon.setStatus(status.longValue());
		return discountRepository.save(coupon);
	}

	@Transactional
	public Page<Discount> findAll(PageRequest request) {
		return discountRepository.findAll(request);
		
	}

	@Transactional
	public void deleteCoupon(Long code) {
		
		 discountRepository.deleteById(code);
	}

	@Transactional
	public List<Discount> findAll() {
		return discountRepository.findAll();
	}

	@Transactional
	public void createCoupon(String code, List<String> emails) {
		Integer status=0;
		List<Discount> discountList = new ArrayList<Discount>();
		for(String email:emails) {
		 Discount coupon=new Discount();
		 coupon.setCoupon(code);
		 coupon.setStatus(status.longValue());
		 coupon.setEmail(email);
		 discountList.add(coupon);
		}
		discountRepository.saveAll(discountList);
	}

	@Transactional
	public List<Discount> findAll(String email) {
		
		return discountRepository.findByEmail(email);
	}
}
