package eu.glowacki.utp.assignment04;

import java.util.Date;

public final class Person implements Comparable<Person> {
	
	private final String _firstName;
	private final String _surname;
	private final Date _birthdate;
	
	public Person(String firstName, String surname, Date birthdate) {
		_firstName = firstName;
		_surname = surname;
		_birthdate = birthdate;
	}

	@Override
	public int compareTo(Person otherPerson) {
		// natural order based on:
		// (1) surname;
		// (2) first name;
		// (3) birth date.
		// TODO Auto-generated method stub
		return 0;
	}
}