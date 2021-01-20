package br.com.kafka.ecommerce.service;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import br.com.kafka.ecommerce.function.ConsumerFunction;
import br.com.kafka.ecommerce.serial.GsonDeserializer;
import jdk.jshell.spi.ExecutionControl.ExecutionControlException;

public class GenericKafkaService<T> implements Closeable {

	private final KafkaConsumer<String, T> consumer;
	private final ConsumerFunction<T> parse;
	
	public GenericKafkaService(String groupIdName, String topic, ConsumerFunction<T> parse, Class<T> clazz) {
		this(parse, groupIdName, clazz, Map.of());
		consumer.subscribe(Collections.singletonList(topic));
	}
	
	public GenericKafkaService(String groupIdName, Pattern topic, ConsumerFunction<T> parse, Class<T> clazz, 
			Map<String, String> properties) {
		
		this(parse, groupIdName, clazz, properties);
		consumer.subscribe(topic);
	}
		
	private GenericKafkaService(ConsumerFunction<T> parse, String groupIdName, Class<T> clazz, Map<String, String> properties) {
		this.parse = parse;
		this.consumer = new KafkaConsumer<>(getProperties(clazz, groupIdName, properties));
	}
	
	public void run() {
		while(true) {
 			ConsumerRecords<String, T> records = consumer.poll(Duration.ofMillis(100));
			
			if(!records.isEmpty()) {
				System.out.println("Found " + records.count() + " register(s).");
				for(var record : records) {
					try {
						parse.consume(record);
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
				}
			}	
		}
	}
	
	private Properties getProperties(Class<T> clazz, String groupIdName, Map<String, String> overrideProperties) {
		var properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupIdName);
		properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
		properties.setProperty(GsonDeserializer.TYPE_CONFIG, clazz.getName());
		properties.putAll(overrideProperties);
		return properties;
	}

	@Override
	public void close() {
		this.close();
	}
}
