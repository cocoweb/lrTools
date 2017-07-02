package com.foresee.test.api.factory;

import java.beans.PropertyVetoException;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBFactory {
	
	final private static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	final private static String ORCL_DRIVER = "oracle.jdbc.driver.OracleDriver";
	
	private ComboPooledDataSource dataSource;
	
	private DBFactory(String dbUrl, String dbUser, String dbPwd, String driver) {
		try {

			dataSource = new ComboPooledDataSource();
			dataSource.setDriverClass(driver);
			dataSource.setJdbcUrl(dbUrl);
			dataSource.setUser(dbUser);
			dataSource.setPassword(dbPwd);
			dataSource.setInitialPoolSize(2);
			dataSource.setMinPoolSize(1);
			dataSource.setMaxPoolSize(10);
			dataSource.setMaxStatements(50);
			dataSource.setMaxIdleTime(60);

			dataSource.setAcquireRetryAttempts(0);
			
		} catch (PropertyVetoException e) {
			throw new RuntimeException(e);
		} 
	}
	
	private static DBFactory getMySqlInstance(String dbUrl, String dbUser, String dbPwd) {
		return new DBFactory(dbUrl, dbUser, dbPwd, MYSQL_DRIVER);
	}
	
	private static DBFactory getOrclInstance(String dbUrl, String dbUser, String dbPwd) {
		return new DBFactory(dbUrl, dbUser, dbPwd, ORCL_DRIVER);
	}
	
//	private Connection getConnection() {
//		try {
//			return dataSource.getConnection();
//		} catch (SQLException e) {
//			throw new RuntimeException("Get Connection Error", e);
//		}
//	}

	public void close() {
		dataSource.close();
	}
	
	public static QueryRunner getMySqlQueryRunner(String dbURL, String username, String password){
		return new QueryRunner(getMySqlInstance(dbURL, username, password).dataSource);
	}
	
	public static QueryRunner getOrclQueryRunner(String dbURL, String username, String password){
		return new QueryRunner(getOrclInstance(dbURL, username, password).dataSource);
	}
	
//	public static Connection getMySqlConnection(String dbURL, String username, String password){
//		return getMySqlInstance(dbURL, username, password).getConnection();
//	}
//	
//	public static Connection getOrclConnection(String dbURL, String username, String password){
//		return getOrclInstance(dbURL, username, password).getConnection();
//	}
}
