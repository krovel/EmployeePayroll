package com.cg;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		String password = "Matrixkashif@1";
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
		return this.getEmplyoeePayrollDataUsingDB(sql);
	}

	public List<EmployeePayrollData> getEmplyoeePayrollDataUsingName(String name) throws DBException {
		List<EmployeePayrollData> employeePayrollList = null;
		if(this.employeePayrollDataStatement == null)
			this.prepareStatementForEmployeeData();
		try {
			employeePayrollDataStatement.setString(1, name);
			ResultSet resultSet = employeePayrollDataStatement.executeQuery();
			employeePayrollList = this.getEmplyoeePayrollDataUsingResultSet(resultSet);			
		} catch (SQLException e) {
			throw new DBException("Cannot execute query", DBException.ExceptionType.SQL_ERROR);
		}
		return employeePayrollList;
	}

	public List<EmployeePayrollData> readEmployeeDataForDateRange(LocalDate startDate, LocalDate endDate) throws DBException {
		String sql = String.format("select * from employee_payroll where start between '%s' and '%s';",
									Date.valueOf(startDate), Date.valueOf(endDate));
		return this.getEmplyoeePayrollDataUsingDB(sql);
	}

	public Map<String, Double> readAverageSalaryByGender() throws DBException {
		String sql = "select gender, avg(salary) as average_salary from employee_payroll group by gender;";
		Map<String, Double> genderToAverageSalaryMap = new HashMap<>();
		try (Connection connection = this.establishConnection()) {
			System.out.println("Connection is successfull!!! " + connection);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				String gender = resultSet.getString("gender");
				double averageSalary = resultSet.getDouble("average_salary");
				genderToAverageSalaryMap.put(gender, averageSalary);
			}
		} catch (SQLException e) {
			throw new DBException("Cannot establish connection",DBException.ExceptionType.CONNECTION_FAIL);
		}
		return genderToAverageSalaryMap;
	}

	private List<EmployeePayrollData> getEmplyoeePayrollDataUsingDB(String sql) throws DBException {
		List<EmployeePayrollData> employeePayrollList = null;
		try (Connection connection = this.establishConnection()) {
			System.out.println("Connection is successfull!!! " + connection);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			employeePayrollList = this.getEmplyoeePayrollDataUsingResultSet(resultSet);
		} catch (SQLException e) {
			throw new DBException("Cannot establish connection",DBException.ExceptionType.CONNECTION_FAIL);
		}
		return employeePayrollList;
	}

	private List<EmployeePayrollData> getEmplyoeePayrollDataUsingResultSet(ResultSet resultSet) throws DBException {
		List<EmployeePayrollData> employeePayrollList = new ArrayList<EmployeePayrollData>();
		try {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String employeeName = resultSet.getString("name");
				double salary = resultSet.getDouble("salary");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				String gender = resultSet.getString("gender");
				employeePayrollList.add(new EmployeePayrollData(id, employeeName, salary, startDate, gender));
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

	@SuppressWarnings("unused")
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

	public EmployeePayrollData addEmployeeToPayroll(String name, double salary, LocalDate startDate, String gender) throws DBException {
		int employeeId = -1;
		EmployeePayrollData newEmployeePayrollData = null;
		Connection connection = null;
		try {
			connection = this.establishConnection();
			connection.setAutoCommit(false);
			System.out.println("Connection is successfull!!! " + connection);
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new DBException("Cannot establish connection", DBException.ExceptionType.CONNECTION_FAIL);			
		}
		try (Statement statement = connection.createStatement()) {
			String sql = String.format("insert into employee_payroll (name, gender, salary, start) values"
									 + "('%s', '%s', %s, '%s')", name, gender, salary, Date.valueOf(startDate));
			int rowsUpdated = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if(rowsUpdated == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if(resultSet.next())
					employeeId = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			throw new DBException("Cannot establish connection", DBException.ExceptionType.STATEMENT_FAILURE);
		}
		try (Statement statement = connection.createStatement()) {
			double deductions = salary * 0.2;
			double incomeTax = (salary - deductions)* 0.1;
			String sql = String.format("insert into payroll (employee_id, basic_pay, deductions, income_tax) values"
									 + "(%s, %s, %s, %s)",employeeId, salary, deductions, incomeTax);
			int rowsUpdated = statement.executeUpdate(sql);
			if(rowsUpdated == 1) 
				newEmployeePayrollData = new EmployeePayrollData(employeeId, name, salary, startDate, gender);				
		} catch (SQLException e) {
			throw new DBException("Cannot establish connection", DBException.ExceptionType.STATEMENT_FAILURE);
		}
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return newEmployeePayrollData;
	}
}