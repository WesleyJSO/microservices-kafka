package br.com.kafka.ecommerce.serial;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonDeserializer<T> implements Deserializer<T> {

	public static final String TYPE_CONFIG = "type_config";
	
	private final Gson gson = new GsonBuilder().create();

	private Class<T> clazz;

	@SuppressWarnings("unchecked")
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		String typeName = String.valueOf(configs.get(TYPE_CONFIG));
		try {
			clazz = (Class<T>) Class.forName(typeName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Type for deserialization doesn't exist.");
	
		}
	}
	
	@Override
	public T deserialize(String topic, byte[] data) {
		return gson.fromJson(new String(data), clazz);
	}
	
	
}
