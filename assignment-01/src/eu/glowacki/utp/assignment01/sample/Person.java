package eu.glowacki.utp.assignment01.sample;

import eu.glowacki.utp.assignment01.IAggregable;
import eu.glowacki.utp.assignment01.IDeeplyCloneable;

public final class Person implements IAggregable<Person, Integer>, IDeeplyCloneable<Person> {
	
	private int _age;
	
	public Person() {
	}
	
	public Person(int age) {
		_age = age;
	}
	
	public int age() {
		return _age;
	}

	public Integer aggregate(Integer intermediateResult) {
		if  (intermediateResult == null) {
			return _age;
		}
		return _age + intermediateResult;
	}
	
	public Person deepClone() {
		return new Person(_age);
	}
}