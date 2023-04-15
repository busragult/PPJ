package eu.glowacki.utp.assignment02.employee;

public final class Manager extends Worker {

	// attributes
	// * subordinates (a list of immediate subordinates)
	// * all subordinates (derived --- i.e. calculated on the fly --- a list of subordinates in all hierarchy)
	
	public Manager(String firstName) {
		super(firstName);
	}
}