package com.microservices.Order.exception;

import com.microservices.Order.dto.ProductResponse;

public class CustomWebClientException  extends RuntimeException{

	
	
	 public CustomWebClientException(String message, Throwable cause) {
	        super(message, cause);
	    }

}
