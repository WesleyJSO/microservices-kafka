package br.com.kafka.ecommerce.entity;

public class Email implements IEntity {

	@SuppressWarnings("unused")
	private final String subject, body;

	public Email(String subject, String body) {
		this.subject = subject;
		this.body = body;
	}
}
