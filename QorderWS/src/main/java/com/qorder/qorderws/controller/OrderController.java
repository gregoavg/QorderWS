package com.qorder.qorderws.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.qorder.qorderws.dto.order.BusinessOrdersDTO;
import com.qorder.qorderws.dto.order.OrderDTO;
import com.qorder.qorderws.exception.BusinessDoesNotExistException;
import com.qorder.qorderws.exception.OrderDoesNotExistException;
import com.qorder.qorderws.model.order.EOrderStatus;
import com.qorder.qorderws.service.IOrderService;

@Controller
@RequestMapping(value = "/orders")
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private IOrderService orderService;

	@RequestMapping(value = "/business", params = "id", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> createOrder(@RequestParam Long id, @RequestBody OrderDTO orderDTO) throws BusinessDoesNotExistException {
		LOGGER.info("Request for order submit");
		orderService.submitOrder(id, orderDTO);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/business", params = "id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BusinessOrdersDTO> getOrdersByBusinessId(@RequestParam Long id) throws BusinessDoesNotExistException {
		BusinessOrdersDTO businessOrders = orderService.fetchOrdersByBusinessID(id);
		LOGGER.info("Request for business orders.\nFetchedList size is " + businessOrders.getOrders().size());
		return new ResponseEntity<BusinessOrdersDTO>(businessOrders, HttpStatus.OK);
	}
	
	@RequestMapping(value ="/business/{businessId}/order", params = "status", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<BusinessOrdersDTO> getOrdersByStatus(@PathVariable Long businessId, @RequestParam String status) throws BusinessDoesNotExistException, IllegalArgumentException {
		EOrderStatus orderStatus = EOrderStatus.valueOf(status);
		BusinessOrdersDTO businessOrders = orderService.fetchOrdersByStatus(businessId, orderStatus);
		LOGGER.info("Request for business orders with status query.\nFetchedList size is " + businessOrders.getOrders().size());
		return new ResponseEntity<BusinessOrdersDTO>(businessOrders, HttpStatus.OK);
	}
	
	@RequestMapping(value ="/order/{orderId}/order", params = "status", method = RequestMethod.POST)
	ResponseEntity<Void> changeOrderStatus(@PathVariable Long orderId, @RequestParam String status) throws OrderDoesNotExistException, IllegalArgumentException {
		EOrderStatus orderStatus = EOrderStatus.valueOf(status);
		orderService.changeOrderStatus(orderId, orderStatus);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}
	@ExceptionHandler(BusinessDoesNotExistException.class)
	ResponseEntity<String> sendNotFoundException(BusinessDoesNotExistException ex) {
		LOGGER.warn("Exception was thrown, with cause " + ex.getCause() + "\nMessage: " + ex.getLocalizedMessage(), ex );
		return new ResponseEntity<String>(ex.getLocalizedMessage().toString(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	ResponseEntity<String> sendBadRequestException(IllegalArgumentException ex) {
		LOGGER.warn("Exception was thrown, with cause " + ex.getCause() + "\nMessage: " + ex.getLocalizedMessage(), ex );
		return new ResponseEntity<String>(ex.getLocalizedMessage().toString(),HttpStatus.BAD_REQUEST);
	}
}
