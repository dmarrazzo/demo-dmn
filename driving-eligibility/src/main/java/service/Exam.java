package service;

import model.Person;

public class Exam {

	public static boolean isPassed(Person person) {
		// all passed except Tony
		return !person.getName().equalsIgnoreCase("tony");
	}
}
