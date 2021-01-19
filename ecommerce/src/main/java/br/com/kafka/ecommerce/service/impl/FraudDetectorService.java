package br.com.kafka.ecommerce.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.kafka.ecommerce.entity.Order;
import br.com.kafka.ecommerce.service.GenericKafkaService;
import br.com.kafka.ecommerce.service.IService;

public class FraudDetectorService implements IService<Order> {

	private static final String topicName = "ECOMMERCE_NEW_ORDER";
	
	public static void main(String...strings) {
		
		var fraudDetectorService = new FraudDetectorService();
		
		try(var service = new GenericKafkaService<>(
				FraudDetectorService.class.getSimpleName(), 
				topicName, 
				fraudDetectorService::parse,
				Order.class)) {
			service.run();			
		}
	}

	@Override
	public final void parse(ConsumerRecord<String, Order> record) {
		print("-----------------------------------------",
				"Processing new order, checking for fraud.",
				record.key(),
				String.valueOf(record.value()),
				String.valueOf(record.partition()),
				String.valueOf(record.offset()));
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("Order was processed.");		
	}
}
