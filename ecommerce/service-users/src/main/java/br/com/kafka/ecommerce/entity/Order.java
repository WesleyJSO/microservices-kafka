package br.com.kafka.ecommerce.entity;


import java.math.BigDecimal;

public class Order implements IEntity {

	private final String orderId, email;
	private final BigDecimal ammount;

	public Order(String orderId, BigDecimal ammount, String email) {
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

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", email=" + email + ", ammount=" + ammount + "]";
	}
}
