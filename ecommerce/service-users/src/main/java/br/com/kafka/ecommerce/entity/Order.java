package br.com.kafka.ecommerce.entity;


import java.math.BigDecimal;

public class Order implements IEntity {

	private final String userId, orderId, email;
	private final BigDecimal ammount;

	public Order(String userId, String orderId, BigDecimal ammount, String email) {
		this.userId = userId;
		this.orderId = orderId;
		this.ammount = ammount;
		this.email = email;
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
	
	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "Order [userId=" + getUserId() + ", orderId=" + getOrderId() + ", ammount=" + ammount + "]";
	}

}
