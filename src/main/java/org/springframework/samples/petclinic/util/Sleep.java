package org.springframework.samples.petclinic.util;

public class Sleep {

	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}
}
