package eu.glowacki.utp.assignment02.employee;

public abstract class Employee extends Person {

	//
	// attributes:
	// * salary (use BigDecimal type for representing currency values)
	// * manager (empty if at top of hierarchy)
	
	protected Employee(String firstName) {
		super(firstName);
	}
}