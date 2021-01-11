package com.jamesreese.carpi.server.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jamesreese.carpi.console.ConsoleService;
import com.jamesreese.carpi.server.jdbc.JDBCDataLogDAO;
import com.jamesreese.carpi.server.model.DataLog;

@RestController
@CrossOrigin
public class DataLogController {
	
	private JDBCDataLogDAO dataLogDao;
	private ConsoleService consoleLog;
	
	public DataLogController(JDBCDataLogDAO dataLog) {
		this.dataLogDao = dataLog;
	}

	//	Given a DataLog object, posts object to database
	@RequestMapping(path = "/log", method = RequestMethod.POST)
	public void postDataLog(@RequestBody DataLog log) {
		dataLogDao.postDataLog(log);
		
		consoleLog.logApiCall("Posted log: " + log.toString());
	}
	
	//	Given a date as String, return all DataLogs at specified date
	@RequestMapping(path = "/logs", method = RequestMethod.GET)
	public List<DataLog> getLogsByDate(@RequestParam("day") String date) {
		consoleLog.logApiCall("Got logs at date: " + date);
		
		return dataLogDao.getLogsByDay(LocalDate.parse(date));
	}
	
	//	Returns all logs at the most recent log date
	@RequestMapping(path = "/logs/recentday", method = RequestMethod.GET)
	public List<DataLog> getLastDaysLog() {
		LocalDate lastDay = dataLogDao.getLastLogDay();
		
		return dataLogDao.getLogsByDay(lastDay);
	}
	
	//	Returns the last date of logging
	@RequestMapping(path = "/recent", method = RequestMethod.GET)
	public LocalDate getLastDay() {
		LocalDate recent = dataLogDao.getLastLogDay();
		
		consoleLog.logApiCall("Got most recent day of: " + recent);
		
		return recent;
	}
}