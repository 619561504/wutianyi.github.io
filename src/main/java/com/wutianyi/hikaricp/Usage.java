package com.wutianyi.hikaricp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hanjiewu on 2016/3/24.
 */
public class Usage {
	public static void main(String[] args) throws SQLException {
		HikariConfig config = new HikariConfig();
		//config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
		config.setUsername("root");
		config.setPassword("860728");
		config.setJdbcUrl("jdbc:mysql://localhost:3306/spring_batch_example");
		//config.setJdbcUrl("jdbc:mysql://localhost:3306/spring_batch_example");
		HikariDataSource hikariDataSource = new HikariDataSource(config);

		Connection connection = hikariDataSource.getConnection();
		ResultSet rs = connection.createStatement().executeQuery("select * from product");
		while (rs.next()) {
			System.out.printf("Name: %s\n", rs.getString("NAME"));
		}
		connection.close();
	}
}
