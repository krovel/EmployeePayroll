package com.cg;

import java.time.LocalDate;

public class EmployeePayrollData {

	private int employeeId;
	private String employeeName;
	private double salary;
	private LocalDate startDate;
	private String gender;

	public EmployeePayrollData() {
	}

	public EmployeePayrollData(int employeeId, String employeeName, double salary) {
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.salary = salary;
	}

	public EmployeePayrollData(int employeeId, String employeeName, double salary, LocalDate startDate) {
		this(employeeId, employeeName, salary);
		this.startDate = startDate;
	}

	
	public EmployeePayrollData(int employeeId, String employeeName, double salary, LocalDate startDate, String gender) {
		this(employeeId, employeeName, salary, startDate);
		this.gender = gender;
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

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Id : " + employeeId + "\t" + "Name : " + employeeName + "\t" + "Gender : " + gender + "\t" + "Salary : "
				+ salary + "\t" + "Start Date : " + startDate;
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
		if (employeeId != other.employeeId)
			return false;
		if (employeeName == null) {
			if (other.employeeName != null)
				return false;
		} else if (!employeeName.equals(other.employeeName))
			return false;
		if (Double.doubleToLongBits(salary) != Double.doubleToLongBits(other.salary))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
}