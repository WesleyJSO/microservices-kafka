package br.com.kafka.ecommerce.service.impl;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.kafka.ecommerce.service.GenericKafkaService;
import br.com.kafka.ecommerce.service.IService;

public class FraudDetectorService implements IService {

	private static final String topicName = "ECOMMERCE_NEW_ORDER";
	
	public static void main(String...strings) {
		
		var fraudDetectorService = new FraudDetectorService();
		
		var service = new GenericKafkaService(FraudDetectorService.class.getSimpleName(), topicName, fraudDetectorService::parse);
		
		service.run();
	}

	@Override
	public final void parse(ConsumerRecord<String, String> record) {
		print("-----------------------------------------",
				"Processing new order, checking for fraud.",
				record.key(),
				record.value(),
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
