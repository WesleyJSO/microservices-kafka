package br.com.kafka.ecommerce.function;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

@FunctionalInterface
public interface ConsumerFunction<T> {

	void consume(ConsumerRecord<String, T> record) throws InterruptedException, ExecutionException;
}
