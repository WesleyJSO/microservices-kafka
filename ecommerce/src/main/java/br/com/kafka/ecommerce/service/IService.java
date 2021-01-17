package br.com.kafka.ecommerce.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IService {

	void parse(ConsumerRecord<String, String> record);
	
	default void print(String...strings) {
		for(String string : strings)
			System.err.println(string);
	}
}
