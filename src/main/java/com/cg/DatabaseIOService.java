package com.cg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseIOService {
	
	private PreparedStatement employeePayrollDataStatement;
	private static DatabaseIOService employeeDBService;

	private DatabaseIOService() {
	}

	public static DatabaseIOService getInstatnce() {
		if(employeeDBService == null)
			employeeDBService = new DatabaseIOService();
		return employeeDBService;
	}

	private Connection establishConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost:3306/payroll_service";
		String userName = "root";
		String password = "First12@";
		System.out.println("Establishing connection to database : " + jdbcURL);
		return DriverManager.getConnection(jdbcURL, userName, password);
	}
	
	private void prepareStatementForEmployeeData() throws DBException {
		String sql = "select * from employee_payroll where name = ?";
		try {
			Connection connection = this.establishConnection();
			System.out.println("Connection is successfull!!! " + connection);
			this.employeePayrollDataStatement = connection.prepareStatement(sql);
		}catch (SQLException e) {
			throw new DBException("Cannot establish connection", DBException.ExceptionType.CONNECTION_FAIL);
		}
	}

	public List<EmployeePayrollData> readData() throws DBException {
		String sql = "select * from employee_payroll;";
		List<EmployeePayrollData> employeePayrollList = null;
		try (Connection connection = this.establishConnection()) {
			System.out.println("Connection is successfull!!! " + connection);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			employeePayrollList = this.getEmplyoeePayrollData(resultSet);

		} catch (SQLException e) {
			throw new DBException("Cannot establish connection",DBException.ExceptionType.CONNECTION_FAIL);
		}
		return employeePayrollList;
	}

	public List<EmployeePayrollData> getEmplyoeePayrollData(String name) throws DBException {
		List<EmployeePayrollData> employeePayrollList = null;
		if(this.employeePayrollDataStatement == null)
			this.prepareStatementForEmployeeData();

		try {
			employeePayrollDataStatement.setString(1, name);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollList = this.getEmplyoeePayrollData(resultSet);			
		} catch (SQLException e) {
			throw new DBException("Cannot execute query", DBException.ExceptionType.SQL_ERROR);
		}
		return employeePayrollList;
	}

	private List<EmployeePayrollData> getEmplyoeePayrollData(ResultSet resultSet) throws DBException {
		List<EmployeePayrollData> employeePayrollList = new ArrayList<EmployeePayrollData>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String employeeName = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, employeeName, salary, startDate));
			}
		} catch (SQLException e) {
			throw new DBException("Cannot populate employee payroll data", DBException.ExceptionType.RETRIEVE_ERROR);
		}
		return employeePayrollList;
	}

	public int updateEmployeeData(String name, double salary) throws DBException {
		return this.updateEmployeeDataUsingPreparedStatement(name, salary);
	}
	private int updateEmployeeDataUsingPreparedStatement(String name, double salary) throws DBException {
		String sql = "update employee_payroll set salary = ? where name = ?";
		try (Connection connection = this.establishConnection()){
			System.out.println("Connection is successfull!!! " + connection);
			PreparedStatement employeePayrollUpdateStatement = connection.prepareStatement(sql);
			employeePayrollUpdateStatement.setDouble(1,salary);
			employeePayrollUpdateStatement.setString(2, name);
			return employeePayrollUpdateStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DBException("Cannot establish connection", DBException.ExceptionType.CONNECTION_FAIL);
		}

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
}