package Shop_For_Home_C1_G7First_Microservice.service;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Shop_For_Home_C1_G7First_Microservice.entity.Cart;
import Shop_For_Home_C1_G7First_Microservice.entity.User;
import Shop_For_Home_C1_G7First_Microservice.enums.ResultEnum;
import Shop_For_Home_C1_G7First_Microservice.exception.MyException;
import Shop_For_Home_C1_G7First_Microservice.repository.CartRepository;
import Shop_For_Home_C1_G7First_Microservice.repository.UsersRepository;

@Service
@DependsOn("passwordEncoder")
public class UserService {
	@Autowired 
	private PasswordEncoder passwordEncoder;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    CartRepository cartRepository;
    
	@PersistenceContext
    private EntityManager entityManager;

    public User findOne(String email) {
        return usersRepository.findByEmail(email);
    }

    public Collection<User> findByRole(String role) {
        return usersRepository.findAllByRole(role);
    }

    @Transactional
    public User save(User users) {
        //registeration
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        try {
            User savedUser = usersRepository.save(users);
            Cart savedCart = cartRepository.save(new Cart(savedUser));
            savedUser.setCart(savedCart);
            return usersRepository.save(savedUser);

        } catch (Exception e) {
            throw new MyException(ResultEnum.VALID_ERROR);
        }
    }

    @Transactional
    public User update(User users) {
        User oldUser = usersRepository.findByEmail(users.getEmail());
        oldUser.setPassword(passwordEncoder.encode(users.getPassword()));
        oldUser.setName(users.getName());
        oldUser.setPhone(users.getPhone());
        oldUser.setAddress(users.getAddress());
        return usersRepository.save(oldUser);
    }

	@Transactional
	public List<User> findAll() {
		return usersRepository.findAll();
	}

	@Transactional
	public Object update(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	public User update(String email) {
		User users=findOne(email);
		users.setRole("ROLE_MANAGER");
		
		return usersRepository.save(users);
	}

	@Transactional
	public Page<User> findAll(PageRequest request) {
		return usersRepository.findAll(request);
	}

	@Transactional
	public User removeAdmin(String email) {
		User users=findOne(email);
		users.setRole("ROLE_CUSTOMER");
		
		return usersRepository.save(users);
	}
	public Session getSession() {
        return entityManager.unwrap(Session.class);
    }
	
	@Transactional
	public String deleteProductByName(String email) {
		
			
			 getSession().createNativeQuery("delete from wishlist where user_email=:email")
				.setParameter("email", email)
				.executeUpdate();
			 usersRepository.deleteUserbyEmail(email);
			 return "Record deleted successfully";
	}
	
//	public void deleteUserbyEmail(String email) {
//		 getSession().createNativeQuery("delete from users where email=:email")
//				.setParameter("email", email)
//				.executeUpdate();
//	}
	
}
