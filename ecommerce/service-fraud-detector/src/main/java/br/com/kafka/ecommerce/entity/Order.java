package br.com.kafka.ecommerce.entity;


import java.math.BigDecimal;

public class Order implements IEntity {

	@SuppressWarnings("unused")
	private final String userId, orderId;
	@SuppressWarnings("unused")
	private final BigDecimal value;

	public Order(String userId, String orderId, BigDecimal value) {
		this.userId = userId;
		this.orderId = orderId;
		this.value = value;
	}
}

