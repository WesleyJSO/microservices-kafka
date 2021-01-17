package br.com.kafka.ecommerce.service;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import br.com.kafka.ecommerce.function.ConsumerFunction;

public class GenericKafkaService implements Closeable {

	private final KafkaConsumer<String, String> consumer;
	private final ConsumerFunction parse;
	
	public GenericKafkaService(String groupIdName, String topic, ConsumerFunction parse) {
		this(parse, groupIdName);
		consumer.subscribe(Collections.singletonList(topic));
		
	}
	
	public GenericKafkaService(String groupIdName, Pattern topic, ConsumerFunction parse) {
		this(parse, groupIdName);
		consumer.subscribe(topic);
		
	}
		
	private GenericKafkaService(ConsumerFunction parse, String groupNameId) {
		this.parse = parse;
		this.consumer = new KafkaConsumer<>(properties(groupNameId));
	}

	public void run() {
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			
			if(!records.isEmpty()) {
				System.out.println("Found " + records.count() + " register(s).");
				for(var record : records) {
					parse.consume(record);
				}
			}	
		}
	}
	
	private static Properties properties(String groupIdName) {
		var properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupIdName);
		properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
		return properties;
	}

	@Override
	public void close() {
		this.close();
	}
}
