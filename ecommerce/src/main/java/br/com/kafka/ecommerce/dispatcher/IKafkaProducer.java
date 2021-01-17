package br.com.kafka.ecommerce.dispatcher;

import java.io.Closeable;
import java.util.concurrent.ExecutionException;

public interface IKafkaProducer extends Closeable {

	public void send(String topic, String key, String value) throws InterruptedException, ExecutionException;
}
