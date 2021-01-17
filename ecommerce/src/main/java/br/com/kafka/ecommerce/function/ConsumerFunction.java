package br.com.kafka.ecommerce.function;

import org.apache.kafka.clients.consumer.ConsumerRecord;

@FunctionalInterface
public interface ConsumerFunction {

	void consume(ConsumerRecord<String, String> record);
}
