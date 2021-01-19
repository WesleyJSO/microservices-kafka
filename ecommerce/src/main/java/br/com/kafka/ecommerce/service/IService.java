package br.com.kafka.ecommerce.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IService<T> {

	void parse(ConsumerRecord<String, T> record);
	
	default void print(String...strings) {
		for(String string : strings)
			System.err.println(string);
	}
}
