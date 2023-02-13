package resources;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class SQLConnection {
	
	public DataSource dataSource;
	
	public SQLConnection() {
		
		
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/hotel_alura?useTimezone=true&serverTimezone=UTC");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("root");
			
		this.dataSource = comboPooledDataSource;
			
		}
	
	public Connection connect() throws SQLException {
		return this.dataSource.getConnection();
	}
	
}
