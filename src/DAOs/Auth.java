package DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import resources.SQLConnection;

public class Auth {
	
	private Connection connection;
	
	public Auth() throws SQLException {
		
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		
		this.connection = connection;
		
	}
	
	public boolean login(String username, String password) {
		String sql = "SELECT password FROM login WHERE username = ?";
		
		try(PreparedStatement pstm = this.connection.prepareStatement(sql)) {
			
			pstm.setString(1, username);
			pstm.execute();
			ResultSet rst = pstm.getResultSet();
			
			boolean isConnected = false;
			
			while(rst.next()) {				
				String senha = (String) rst.getString(1);
			
				if(senha.equals(password)){
					isConnected = true;
				} 
			}
		
		return isConnected;
		
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	
	}
}
