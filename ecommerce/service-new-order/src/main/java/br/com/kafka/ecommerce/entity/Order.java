package br.com.kafka.ecommerce.entity;

import java.math.BigDecimal;

public class Order implements IEntity {

	@SuppressWarnings("unused")
	private final String userId, orderId;
	@SuppressWarnings("unused")
	private final BigDecimal ammount;

	public Order(String userId, String orderId, BigDecimal ammount) {
		this.userId = userId;
		this.orderId = orderId;
		this.ammount = ammount;
	}
}

