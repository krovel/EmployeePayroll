package com.cg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseIOService {

	public List<EmployeePayrollData> readData() throws DBException {
		String sql = "select * from employee_payroll;";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<EmployeePayrollData>();
		try (Connection connection = this.establishConnection()) {
			System.out.println("Connection is successfull!!! " + connection);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, salary, startDate));
			}
		} catch (SQLException e) {
			throw new DBException("Cannot establish connection",DBException.ExceptionType.CONNECTION_FAIL);
		}
		return employeePayrollList;
	}

	public EmployeePayrollData getEmplyoeePayrollData(String name) throws DBException {
		EmployeePayrollData employeePayrollData = null;
		String sql = String.format("select * from employee_payroll where name = '%s'", name);
		try (Connection connection = this.establishConnection()) {
			System.out.println("Connection is successfull!!! " + connection);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String employeeName = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollData = new EmployeePayrollData(id, employeeName, salary, startDate);
			}
		} catch (SQLException e) {
			throw new DBException("Cannot establish connection", DBException.ExceptionType.CONNECTION_FAIL);
		}
		return employeePayrollData;
	}

	public int updateEmployeeData(String name, double salary) throws DBException {
		return this.updateEmployeeDataUsingStatement(name, salary);
	}

	private int updateEmployeeDataUsingStatement(String name, double salary) throws DBException {
		String sql = String.format("update employee_payroll set salary = %.2f where name = '%s'", salary, name);
		try (Connection connection = this.establishConnection()) {
			System.out.println("Connection is successfull!!! " + connection);
			Statement statement = connection.createStatement();
			return statement.executeUpdate(sql);
		} catch (SQLException e) {
			throw new DBException("Cannot establish connection", DBException.ExceptionType.CONNECTION_FAIL);
		}
	}

	private Connection establishConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
		String userName = "root";
		String password = "Matrixkashif@1";
		System.out.println("Establishing connection to database : " + jdbcURL);
		return DriverManager.getConnection(jdbcURL, userName, password);
	}
}