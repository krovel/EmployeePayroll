package com.cg;

import java.time.LocalDate;
import java.util.List;

public class EmployeePayrollData {

	private int employeeId;
	private String employeeName;
	private double salary;
	private List<LocalDate> startDates;
	private String gender;
	private String companyName;
	private List<String> phoneNumbers;
	private List<String> departmentNames;

	public EmployeePayrollData() {
	}

	public EmployeePayrollData(int employeeId, String employeeName, double salary) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.salary = salary;
	}

	public EmployeePayrollData(int employeeId, String employeeName, double salary, List<LocalDate> startDates) {
		this(employeeId, employeeName, salary);
		this.startDates = startDates;
	}

	
	public EmployeePayrollData(int employeeId, String employeeName, double salary, List<LocalDate> startDates, String gender) {
		this(employeeId, employeeName, salary, startDates);
		this.gender = gender;
	}
	

	public EmployeePayrollData(int employeeId, String employeeName, double salary, List<LocalDate> startDates,
			String gender, String companyName, List<String> phoneNumbers, List<String> departmentNames) {
		this(employeeId, employeeName, salary, startDates, gender);
		this.companyName = companyName;
		this.phoneNumbers = phoneNumbers;
		this.departmentNames = departmentNames;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<LocalDate> getStartDates() {
		return startDates;
	}

	public void setStartDates(List<LocalDate> startDates) {
		this.startDates = startDates;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<String> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(List<String> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}

	public List<String> getDepartmentNames() {
		return departmentNames;
	}

	public void setDepartmentNames(List<String> departmentNames) {
		this.departmentNames = departmentNames;
	}

	@Override
	public String toString() {
		return "Id : " + employeeId + "\t" + "Name : " + employeeName + "\t" + "Gender : " + gender + "\t" + "Salary : "
				+ salary + "\t" + "Company Name : " + companyName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
		result = prime * result + employeeId;
		result = prime * result + ((employeeName == null) ? 0 : employeeName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		long temp;
		temp = Double.doubleToLongBits(salary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeePayrollData other = (EmployeePayrollData) obj;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (employeeName == null) {
			if (other.employeeName != null)
				return false;
		} else if (!employeeName.equals(other.employeeName))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
			return false;
		if (Double.doubleToLongBits(salary) != Double.doubleToLongBits(other.salary))
			return false;
		return true;
	}	
}