package br.com.kafka.ecommerce.entity;

import java.math.BigDecimal;

public class Order implements IEntity {

	private final String userId, orderId;
	private final BigDecimal ammount;
	private final String email;
	
	public Order(String userId, String orderId, BigDecimal ammount, String email) {
		this.userId = userId;
		this.orderId = orderId;
		this.ammount = ammount;
		this.email = email;
	}
}

