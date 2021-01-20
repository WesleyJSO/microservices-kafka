package br.com.kafka.ecommerce;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import br.com.kafka.ecommerce.dispatcher.GenericKafkaProducer;
import br.com.kafka.ecommerce.entity.Email;
import br.com.kafka.ecommerce.entity.Order;

public class NewOrder {

	private static final String topicNewOrder = "ECOMMERCE_NEW_ORDER";
	private static final String topicSendEmail = "ECOMMERCE_SEND_EMAIL";
	
	public static void main(String...strings) throws InterruptedException, ExecutionException {
		
		try(var orderDispatcher = new GenericKafkaProducer<Order>();
			var emailDispatcher = new GenericKafkaProducer<Email>()) {
		
			for (int i = 0; i < 10; i++) {
				var userId = UUID.randomUUID().toString();
				var orderId = UUID.randomUUID().toString();
				var ammount = new BigDecimal(Math.random() * 5000 + 1);
				
				var order = new Order(userId, orderId, ammount);
				
				orderDispatcher.send(topicNewOrder, userId, order);
				
				var email = new Email("Emil subject", "Thank you for your order! We are processing your order!");
				emailDispatcher.send(topicSendEmail, userId, email);
			}
		}
	}	
}
