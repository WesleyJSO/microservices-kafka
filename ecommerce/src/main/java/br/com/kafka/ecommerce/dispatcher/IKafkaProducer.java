package br.com.kafka.ecommerce.dispatcher;

import java.io.Closeable;
import java.util.concurrent.ExecutionException;

import br.com.kafka.ecommerce.entity.IEntity;

public interface IKafkaProducer<T extends IEntity> extends Closeable {

	public void send(String topic, String key, T value) throws InterruptedException, ExecutionException;
}
