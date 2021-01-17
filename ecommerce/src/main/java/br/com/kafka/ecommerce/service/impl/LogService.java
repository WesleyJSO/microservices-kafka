package br.com.kafka.ecommerce.service.impl;

import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import br.com.kafka.ecommerce.service.GenericKafkaService;
import br.com.kafka.ecommerce.service.IService;

public class LogService implements IService {

	private static final Pattern topicName = Pattern.compile("ECOMMERCE.*");
	
	public static void main(String...strings) {
		
		var logService = new LogService();
		
		var service = new GenericKafkaService(LogService.class.getSimpleName(), topicName, logService::parse);
		
		service.run();
		
	}
	
	@Override
	public void parse(ConsumerRecord<String, String> record) {
		print("-----------------------------------------",
				"LOG::: " + record.topic(),
					record.key(),
					record.value(),
					String.valueOf(record.partition()),
					String.valueOf(record.offset()));
	}
}
