package com.jamesreese.carpi.logreader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.jamesreese.carpi.model.DataLog;

public class LogFileReader {
	private BufferedReader reader;

	public LogFileReader(String path) {
		initializeScanner(path);
	}
	
	// Reads the next line of the csv file using BufferedReader.readLine()
	public String getNextLine() throws IOException {
		String line = reader.readLine();
		return line;
	}
	
	//	Reads next line of csv file and returns a DataLog object
	public DataLog returnLineAsObject() throws IOException {
		DataLog log = new DataLog();
		
		//	Get next line of file and return as a String, null if there is no next line
		String line = getNextLine();
		//	Create an array of Strings to hold the delimited values of the line
		String[] splitLine;
		
		//	if line has data
		//		Split the line using a delimiter of ", "
		if(line != null) {
			splitLine = line.split(", ");
		}
		else {
			return null;
		}
		
		//	Create a date and time formatter to format the date and time as desired
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		//	Try formatting date and time and setting DataLog values to values in line
		try {
			//	Format the date and time using DatTimeFormatter objects
			String formattedDate = LocalDate.parse(splitLine[0]).format(dateFormatter);
			String formattedTime = LocalTime.parse(splitLine[1]).format(timeFormatter);
			
			//	Set DataLog object values equal to values stored in the line
			log.setDate(LocalDate.parse(formattedDate));
			log.setTime(LocalTime.parse(formattedTime));
			log.setValue(Double.parseDouble(splitLine[2]));
			log.setDataType(splitLine[3]);
		}
		//	Catch FailedToParse exception and print it
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return log;
	}

	public void close() throws IOException {
		reader.close();
	}
	
	private void initializeScanner(String path) {
		//	Try creating a file at the file path passed and passing file into BufferedReader object
		try {
			FileReader logFile = new FileReader(path);
			reader = new BufferedReader(logFile);
		}
		//	Catch FileNotFoundException
		//		Alert user to the error
		//		Exit program
		catch(FileNotFoundException e) {
			System.out.println("No such file exists at the path specified:");
			System.out.print(path);
			System.exit(1);
		}
		System.out.println("Scanner initialized.  Ready to read");
	}
}