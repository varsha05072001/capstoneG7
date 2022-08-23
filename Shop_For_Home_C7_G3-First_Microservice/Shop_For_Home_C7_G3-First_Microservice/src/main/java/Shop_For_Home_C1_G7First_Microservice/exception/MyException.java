package Shop_For_Home_C1_G7First_Microservice.exception;

import Shop_For_Home_C1_G7First_Microservice.enums.ResultEnum;

public class MyException extends RuntimeException {

    private Integer code;
    
    

    public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public MyException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());

        this.code = resultEnum.getCode();
    }

    public MyException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
