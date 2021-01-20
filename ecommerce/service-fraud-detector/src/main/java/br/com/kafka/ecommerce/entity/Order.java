package br.com.kafka.ecommerce.entity;


import java.math.BigDecimal;

public class Order implements IEntity {

	private final String userId, orderId;
	
	private final BigDecimal ammount;

	public Order(String userId, String orderId, BigDecimal ammount) {
		this.userId = userId;
		this.orderId = orderId;
		this.ammount = ammount;
	}

	public BigDecimal getAmmount() {
		return ammount;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getUserId() {
		return userId;
	}
	
	@Override
	public String toString() {
		return "Order [userId=" + getUserId() + ", orderId=" + getOrderId() + ", ammount=" + ammount + "]";
	}
}
