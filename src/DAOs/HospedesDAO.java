package DAOs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import resources.SQLConnection;

public class HospedesDAO {

	private Connection connection;
	private String id;
	
	public HospedesDAO() throws SQLException {
		
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		
		this.connection = connection;
		
	}
	
	public String getId() {
		return this.id;
			
	}
	
	public void saveHospede(String name, String surname, Date birthDate, String nacionality, String telephone, int idReserva) throws SQLException {
		String sql = "INSERT INTO hospedes (Nome, Sobrenome, DataNascimento, Nacionalidade, Telefone, IdReserva) VALUES (?, ?, ?, ?, ?, ?)";
		
		try(PreparedStatement pstm = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			pstm.setString(1, name);
			pstm.setString(2, surname);
			pstm.setDate(3, birthDate);
			pstm.setString(4, nacionality);
			pstm.setString(5, telephone);
			pstm.setInt(6, idReserva);
			
			pstm.execute();
			
			try(ResultSet rst = pstm.getGeneratedKeys()) {
				while(rst.next()) {				
					this.id = (String) rst.getString(1);
					} 
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
			
	}

	public List<Map<String, Object>> getRowBySurname(String buscarText) {
		String sql = "SELECT * FROM hospedes WHERE Sobrenome = ?";
		
		try(PreparedStatement pstm = this.connection.prepareStatement(sql)) {
			
			pstm.setString(1, buscarText);
			pstm.execute();
			ResultSet rst = pstm.getResultSet();
			
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			Map<String, Object> row = null;

		    ResultSetMetaData metaData = rst.getMetaData();
		    Integer columnCount = metaData.getColumnCount();

		    while (rst.next()) {
		        row = new HashMap<String, Object>();
		        
		        row.put("Id", rst.getString(1));
		        row.put("Nome", rst.getString(2));
		        row.put("Sobrenome", rst.getString(3));
		        row.put("DataNascimento", rst.getDate(4));
		        row.put("Nacionalidade", rst.getString(5));
		        row.put("Telefone", rst.getString(6));
		        row.put("IdReserva", rst.getString(7));
		        
		        resultList.add(row);
		    }
		
		return resultList;
		
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
