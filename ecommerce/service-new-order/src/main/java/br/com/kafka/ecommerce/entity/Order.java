package br.com.kafka.ecommerce.entity;

import java.math.BigDecimal;

public class Order implements IEntity {

	@SuppressWarnings("unused")
	private final String orderId, email;
	@SuppressWarnings("unused")
	private final BigDecimal ammount;
	
	public Order(String orderId, BigDecimal ammount, String email) {
		this.orderId = orderId;
		this.ammount = ammount;
		this.email = email;
	}
}

