package eu.glowacki.utp.assignment02.test;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import eu.glowacki.utp.assignment02.HumanResourcesStatistics;
import eu.glowacki.utp.assignment02.employee.Employee;
import eu.glowacki.utp.assignment02.employee.Manager;
import eu.glowacki.utp.assignment02.payroll.PayrollEntry;

public class HumanResourcesStatisticsTest {
	
	// Create a HR structure which resembles the below one:
	//
	// Director (an instance of Manager class (Director does not have a manager)
	//   |- Manager01
	//        |- Worker
	//        |- Worker
	//        |- Trainee
	//        |- ...
	//   |- Manager02
	//        |- ...
	//   |- ...
	//   |- Worker
	//   |- Worker
	//   |- Trainee
	
	private List<Employee> _allEmployees; // all employees ---  i.e. Workers, Trainees and their Managers and top Director (also an instance of Manager class)
	
	@Test
	public void payroll() {
		HumanResourcesStatistics.payroll(_allEmployees);
	}

	@Test
	public void subordinatesPayroll() {
		HumanResourcesStatistics.subordinatesPayroll(null);
	}

	@Test
	public void bonusTotal() {
		BigDecimal total = HumanResourcesStatistics.bonusTotal(_allEmployees);
		Assert.assertEquals(new BigDecimal("100"), total);
	}

	/// ...
	// rest of the methods specified in the assignment description
}