package br.com.kafka.ecommerce;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import br.com.kafka.ecommerce.service.GenericKafkaService;
import br.com.kafka.ecommerce.service.IService;

public class EmailService implements IService<String> {

	private static final String ECOMMERCE_SEND_EMAIL = "ECOMMERCE_SEND_EMAIL";
	
	public static void main(String...strings) {
		
		var emailService = new EmailService();
		
		try(var service = new GenericKafkaService<>(
				EmailService.class.getSimpleName(), 
				ECOMMERCE_SEND_EMAIL, 
				emailService::parse,
				String.class,
				Map.ofEntries(Map.entry(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())))) {
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
			e.printStackTrace();
		}
		print("E-mail sent.");
	}
}
