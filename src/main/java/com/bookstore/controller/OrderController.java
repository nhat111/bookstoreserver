package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.model.Order;
import com.bookstore.repository.OrderRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderRepository ordersrepository;
	
	

	@GetMapping("/orders")
	public List<Order> getAllOrders() {
		return ordersrepository.findAll();
	}
	
	@GetMapping("/orders/{username}")
	public List<Order> getOrdersByUser(@PathVariable(value = "username")String userName){
		return ordersrepository.findByUserName(userName);
	}

	@PostMapping("/orders")
	public Order createOrders(@RequestBody Order orders) {
		return ordersrepository.save(orders);
	}

	@PutMapping("/orders/{id}")
	public ResponseEntity<Order> updateOrders(@PathVariable(value = "id") Long ordersId,
			@RequestBody Order deiltalOrders) throws Exception {
		Order orders = ordersrepository.findById(ordersId).orElseThrow(() -> new Exception("orders not found"));

		orders.setBookName(deiltalOrders.getBookName());
		final Order ordersUpdate = ordersrepository.save(orders);
		return ResponseEntity.ok().body(ordersUpdate);

	}
	@DeleteMapping("/orders/{id}")
	public void deleteOrders(@PathVariable(value = "id")Long ordersId) throws Exception{
		Order orders = ordersrepository.findById(ordersId)
				.orElseThrow(()-> new Exception("oders not found"));
		ordersrepository.delete(orders);
	}

}
