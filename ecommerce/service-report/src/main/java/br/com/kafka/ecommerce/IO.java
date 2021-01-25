package br.com.kafka.ecommerce;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

public class IO {

	public static boolean copy(Path source, Path target, StandardCopyOption... options) {
		target.toFile().getParentFile().mkdirs();
		try {
			Files.copy(source, target.toFile().toPath(), options);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean append(Path target, String value) {
		try {
			Files.write(target.toFile().toPath(), value.getBytes(), StandardOpenOption.APPEND);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
