package com.jamesreese.carpi;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.jamesreese.carpi.logreader.LogFileReader;
import com.jamesreese.carpi.model.DataLog;

public class CarPi {
	
	private static final String API_URL = "http://localhost:8080/";
	private static final String FILE_PATH = "LogDataCsv.csv";
	private static RestTemplate api = new RestTemplate();
	
	public static void main(String args[]) {
		run();
	}
	
	private static void run() {
		System.out.println("Welcome to the Data Log Upload Application");
		System.out.println("Uploading will now commence \n"
							+ "Enjoy :)");
		
		
		//	Creates a new LogFileReader to be used for reading from data log file
		LogFileReader reader = new LogFileReader(FILE_PATH);
		
		try {
			//	Read through file and posts data to database
			readThroughFile(reader);
			//	Closes file
			reader.close();
			System.out.println("All done!");
		}
		catch(Exception ex) {
			ex.printStackTrace();
			System.out.println("An error ocurred while reading the file.  Please ensure content is still there.");
		}
		finally {
			System.out.println("Enjoy your day :)");
		}
	}
	
	//	given a LogFileReader object, will read through file and post to a db one line at a time
	private static void readThroughFile(LogFileReader reader) throws IOException {
		//	Creates HttpHeaders and sets content type for use in API Calls
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		//	Declare HttpEntity object to use for posting using API
		HttpEntity<DataLog> logEntity;
		
		//	Reads first line of data log file
		DataLog log = reader.returnLineAsObject();
		
		//	Loops until returned a null value from nextLine call
		while(log != null) {
			//	set value of HttpEntity to current line in file
			logEntity = new HttpEntity<DataLog>(log, headers);
			
			//	POST log at current line to the db
			api.postForObject(API_URL + "log", logEntity, DataLog.class);
			
			//	read next line in file
			log = reader.returnLineAsObject();
		}
	}
}
