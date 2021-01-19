package br.com.kafka.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.kafka.ecommerce.service.GenericKafkaService;
import br.com.kafka.ecommerce.service.IService;

public class EmailService implements IService<String> {

	private static final String topicName = "ECOMMERCE_SEND_EMAIL";
	
	public static void main(String...strings) {
		
		var emailService = new EmailService();
		
		try(var service = new GenericKafkaService<>(
				EmailService.class.getSimpleName(), 
				topicName, 
				emailService::parse,
				String.class)) {
			service.run();
		}
	}
	
	@Override
	public final void parse(ConsumerRecord<String, String> record) {
		print("-----------------------------------------",
				"Sending e-mail, checking for fraud.",
				record.key(),
				record.value(),
				String.valueOf(record.partition()),
				String.valueOf(record.offset()));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print("E-mail sent.");
	}
}
