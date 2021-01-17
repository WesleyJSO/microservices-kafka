package br.com.kafka.ecommerce;

import java.time.Duration;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class LogService {

	public static void main(String...strings) throws InterruptedException {
		
		var consumer = new KafkaConsumer<String, String>(properties());
		
		consumer.subscribe(Pattern.compile("ECOMMERCE.*"));
		
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			
			if(!records.isEmpty()) {
				print("Found " + records.count() + " register(s).");
				for(var record : records) {
					print("-----------------------------------------",
							"LOG::: " + record.topic(),
							record.key(),
							record.value(),
							String.valueOf(record.partition()),
							String.valueOf(record.offset()));
				}
			}
			
		}
	}

	private static Properties properties() {
		var properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, LogService.class.getSimpleName());
		return properties;
	}
	
	private static void print(String...strings) {
		for(String string : strings)
			System.err.println(string);
	}
}
