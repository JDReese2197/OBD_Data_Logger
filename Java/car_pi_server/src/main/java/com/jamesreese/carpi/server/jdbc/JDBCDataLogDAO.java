package com.jamesreese.carpi.server.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.jamesreese.carpi.server.model.DataLog;

@Component
public class JDBCDataLogDAO {
	
	private JdbcTemplate jdbcTemplate;

	private JDBCDataLogDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	//	Posts a data log to the database
	public void postDataLog(DataLog log) {
		String query = "INSERT INTO car_log (date_stamp, time_stamp, value, log_type) VALUES (?, ?, ?, ?)";
	
		jdbcTemplate.update(query, log.getDate(), log.getTime(), log.getValue(), log.getDataType());
	}
	
	//	Given a date, get all data logs with that date
	public List<DataLog> getLogsByDay(LocalDate date) {
		List<DataLog> logs = new ArrayList<>();
		
		String query = "SELECT * FROM car_log WHERE date_stamp = ? ORDER BY time_stamp DESC";
		SqlRowSet results = jdbcTemplate.queryForRowSet(query, date);
		
		if(!results.next()) {
			return null;
		}
		else {
			logs.add( mapRowToLog(results) );
			while(results.next()) {
				logs.add( mapRowToLog(results) );
			}
		}
		
		return logs;
	}
	
	//	Returns the date of the most recent data log.
	public LocalDate getLastLogDay() {
		String query = "SELECT date_stamp FROM car_log ORDER BY date_stamp DESC LIMIT 1";
		SqlRowSet result = jdbcTemplate.queryForRowSet(query);
		
		if(result.next()) {
			return result.getDate("date_stamp").toLocalDate();
		}
		else {
			return null;
		}
	}
	
	//	Given two dates, a start and end, get all data logs between the two
	public List<DataLog> getLogsBetweenDates(LocalDate start, LocalDate end) {
		List<DataLog> logs = new ArrayList<>();
		
		String query = "SELECT * FROM car_log WHERE date_stamp BETWEEN ? AND ? ORDER BY date_stamp DESC;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(query, start, end);
		
		if(!results.next()) {
			return null;
		}
		else {
			logs.add( mapRowToLog(results) );
			while(results.next()) {
				logs.add( mapRowToLog(results) );
			}
		}
		
		return logs;
	}
	
	//	Given a type (String) and a limit, get limit amount of data logs with log_type of type
	public List<DataLog> getLogsByType(String type, int limit) {
		List<DataLog> logs = new ArrayList<>();
		type = type.toLowerCase();
		
		String query = "SELECT * FROM car_log WHERE log_type = ? LIMIT ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(query, type, limit);
		
		if(!results.next()) {
			return null;
		}
		else {
			logs.add( mapRowToLog(results) );
			while(results.next()) {
				logs.add( mapRowToLog(results) );
			}
		}
		
		return logs;
	}
	
	//	Takes a RowSet and returns a DataLog object with data from row
	private DataLog mapRowToLog(SqlRowSet row) {
		DataLog log = new DataLog();

		log.setId(row.getInt("id"));
		log.setDate(row.getDate("date_stamp").toLocalDate());
		log.setTime(row.getTime("time_stamp").toLocalTime());
		log.setValue(row.getDouble("value"));
		log.setDataType(row.getString("log_type"));
		
		return log;
	}
}