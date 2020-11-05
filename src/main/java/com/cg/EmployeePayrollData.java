package com.cg;

import java.time.LocalDate;

public class EmployeePayrollData {

	private int employeeId;
	private String employeeName;
	private double salary;
	private LocalDate startDate;

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

	@Override
	public String toString() {
		return "Id : " + employeeId + "\t" + "Name : " + employeeName + "\t" + "Salary : " + salary + "\t" + "Start Date : " + startDate;
	}
}