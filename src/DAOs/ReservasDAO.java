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

public class ReservasDAO {

	private Connection connection;
	private LocalDateTime from;
	private LocalDateTime to;
	private double valorDaReserva;
	private String numeroDaReserva;
	private String formaPagamento;
	// Valor da diária
	private double valorDaDiaria = 300.0;
	
	public ReservasDAO() throws SQLException {
		
		SQLConnection sqlConnection = new SQLConnection();
		Connection connection = sqlConnection.connect();
		
		this.connection = connection;
		
	}

	
	public double calcReserva(LocalDateTime from, LocalDateTime to, String formaPagamento) {
		this.from = from;
		this.to = to;
		this.formaPagamento = formaPagamento;
		
		Duration d = Duration.between(this.from, this.to);
		this.valorDaReserva = this.valorDaDiaria * d.toDays();
		
		return this.valorDaReserva;
	}	
	
	public String getReservaNumber() {
		return this.numeroDaReserva;
			
	}
	
	public void cancelReserva(int id) {
		String sql = "DELETE FROM reservas WHERE Id=?;";
		
		try(PreparedStatement pstm = this.connection.prepareStatement(sql)) {
			
			pstm.setInt(1, id);
			
			pstm.execute();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
			
	}
	
	public void saveReserva() throws SQLException {
		String sql = "INSERT INTO reservas (DataEntrada, DataSaida, Valor, FormaPagamento) VALUES (?, ?, ?, ?)";
		
		try(PreparedStatement pstm = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			
			pstm.setDate(1, Date.valueOf(this.from.toLocalDate()));
			pstm.setDate(2, Date.valueOf(this.to.toLocalDate()));
			pstm.setDouble(3, this.valorDaReserva);
			pstm.setString(4, this.formaPagamento);
			
			pstm.execute();
			
			try(ResultSet rst = pstm.getGeneratedKeys()) {
				while(rst.next()) {				
					this.numeroDaReserva = (String) rst.getString(1);
					} 
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		}
			
	}


	public List<Map<String, Object>> getRowById(String buscarText) {
		String sql = "SELECT * FROM reservas WHERE Id = ?";
		
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
		        row.put("DataEntrada", rst.getDate(2));
		        row.put("DataSaida", rst.getDate(3));
		        row.put("Valor", rst.getString(4));
		        row.put("FormaPagamento", rst.getString(5));
		        
		        resultList.add(row);
		    }
		
		return resultList;
		
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
