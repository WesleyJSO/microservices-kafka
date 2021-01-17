package br.com.kafka.ecommerce.dispatcher.impl;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import br.com.kafka.ecommerce.dispatcher.GenericKafkaProducer;

public class NewOrder {

	private static final String topicNewOrder = "ECOMMERCE_NEW_ORDER";
	private static final String topicSendEmail = "ECOMMERCE_SEND_EMAIL";
	
	public static void main(String...strings) throws InterruptedException, ExecutionException {
		
		try(var dispatcher = new GenericKafkaProducer()) {
		
			for (int i = 0; i < 10; i++) {
				var key = UUID.randomUUID().toString();
			
				var value = key + ", 321, 222";
				dispatcher.send(topicNewOrder, key, value);
				
				var email = "Thank you for your order! We are processing your order!";
				dispatcher.send(topicSendEmail, key, email);
			}
		}
	}	
}
