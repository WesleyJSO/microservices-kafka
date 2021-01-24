package br.com.kafka.ecommerce;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;

import br.com.kafka.ecommerce.service.GenericKafkaService;
import br.com.kafka.ecommerce.service.IService;

public class LogService implements IService<String> {

	private static final Pattern ECOMMERCE_ALL = Pattern.compile("ECOMMERCE.*");
	
	public static void main(String...strings) {
		
		var logService = new LogService();
		
		try(var service = new GenericKafkaService<>(
				LogService.class.getSimpleName(), 
				ECOMMERCE_ALL, 
				logService::parse, 
				String.class,
				Map.ofEntries(Map.entry(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName())))) {
			service.run();
		}		
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
