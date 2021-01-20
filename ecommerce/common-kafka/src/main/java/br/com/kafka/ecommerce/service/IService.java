package br.com.kafka.ecommerce.service;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IService<T> {

	void parse(ConsumerRecord<String, T> record) throws InterruptedException, ExecutionException;
	
	default void print(String...strings) {
		for(String string : strings)
			System.err.println(string);
	}
}
