package br.com.kafka.ecommerce.dispatcher;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import br.com.kafka.ecommerce.entity.IEntity;
import br.com.kafka.ecommerce.serial.GsonSerializer;

public class GenericKafkaProducer<T extends IEntity> implements IKafkaProducer<T> {

	private final KafkaProducer<String, T> producer;
	
	public GenericKafkaProducer() {
		this.producer = new KafkaProducer<>(properties());
	}
	
	@Override
	public void send(String topic, String key, T value) throws InterruptedException, ExecutionException {
		
		var record = new ProducerRecord<>(topic, key, value);
		
		producer.send(record, this.callback()).get();
	}

	private Callback callback() {
		return (data, exeption) -> {
			if(exeption != null) {
				exeption.printStackTrace();
				return;
			}
			System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset() + "/ timestamp " + data.timestamp());
		};
	}
	
	private static Properties properties() {
		var properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
		return properties;
	}

	@Override
	public void close() {
		producer.close();
	}
}
