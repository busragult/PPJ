package eu.glowacki.utp.assignment08;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Date;
import java.util.List;

public final class PersonDatabase {

	// assignment 8 - factory method based on deserialization
	public static PersonDatabase deserialize(DataInputStream input) throws Assignment08Exception {
		return null;
	}

	// assignment 8
	public void serialize(DataOutputStream output) throws Assignment08Exception {

	}

	// assignment 4
	public List<Person> sortedByFirstName() {
		return null;
	}

	// assignment 4
	public List<Person> sortedBySurnameFirstNameAndBirthdate() {
		return null;
	}

	// assignment 4
	public List<Person> sortedByBirthdate() {
		return null;
	}

	// assignment 4
	public List<Person> bornOnDay(Date date) {
		return null;
	}
}