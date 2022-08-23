package Shop_For_Home_C1_G7First_Microservice.response;

import java.util.List;

import Shop_For_Home_C1_G7First_Microservice.entity.User;


public class UserListResponse {
	
	private List<User> userList;

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	

}
