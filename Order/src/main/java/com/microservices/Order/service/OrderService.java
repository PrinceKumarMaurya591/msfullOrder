package com.microservices.Order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.microservices.Order.dto.OrderRequest;
import com.microservices.Order.dto.OrderResponse;
import com.microservices.Order.dto.ProductResponse;
import com.microservices.Order.entity.Order;
import com.microservices.Order.exception.CustomWebClientException;
import com.microservices.Order.repo.OrderRepository;

import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    
    
    
//    @Value("${order-service.url}")
//    private String orderServiceUrl;

    @Autowired
    private WebClient.Builder webClientBuilder;

   

    public ProductResponse getProductByIdFromProductService(Long productId) {
    	 try {
    	        // Make a GET request to the Product Service to get product details by ID
    	        return webClientBuilder.build()
    	                .get()
    	                .uri("http://product-service/api/products/{productId}", productId)
    	                .retrieve()
    	                .bodyToMono(ProductResponse.class)
    	                .block();
    	    } catch (WebClientResponseException e) {
    	        // Handle specific HTTP error responses
    	        // You can log the error, return a default value, or throw your custom exception
    	        return handleWebClientResponseException(e);
    	    } catch (Exception e) {
    	        // Handle other exceptions
    	        return handleGenericException(e);
    	    }
    	}

    	private ProductResponse handleWebClientResponseException(WebClientResponseException e) {
    	    // Example: Log the error and throw your custom exception
    	    // log.error("Error during WebClient request: {}", e.getMessage());
    	    // throw new CustomWebClientException("Error during WebClient request", e);
    	    throw new CustomWebClientException("Error during WebClient request", e);
    	}

    	private ProductResponse handleGenericException(Exception e) {
    	    // Example: Log the error and throw your custom exception
    	    // log.error("Unexpected error: {}", e.getMessage());
    	    // throw new CustomWebClientException("Unexpected error", e);
    	    throw new CustomWebClientException("Unexpected error", e);
    	}
    

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(OrderRequest orderRequest) {
        // Convert OrderRequest to Order
        Order newOrder = new Order();
        newOrder.setCustomerName(orderRequest.getCustomerName());
        newOrder.setTotalAmount(orderRequest.getTotalAmount());

        // Save the new order to the database
        return orderRepository.save(newOrder);
    }

    public Order updateOrder(Long id, OrderRequest orderRequest) {
        // Find the order by id
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            // Update the fields of the existing order
            Order existingOrder = optionalOrder.get();
            existingOrder.setCustomerName(orderRequest.getCustomerName());
            existingOrder.setTotalAmount(orderRequest.getTotalAmount());

            // Save the updated order to the database
            return orderRepository.save(existingOrder);
        } else {
            // If the order with the given id is not found, you may choose to throw an exception or return null
            return null;
        }
    }

    public void deleteOrder(Long id) {
        // Delete the order by id
        orderRepository.deleteById(id);
    }
}