package com.jamesreese.carpi.test.logreader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.jamesreese.carpi.logreader.LogFileReader;
import com.jamesreese.carpi.model.DataLog;

public class LogFileReaderTest {
	private static final String FILE_PATH = "TestLogData.csv";
	private static LogFileReader fileReader;
	private static File testFile;
	private FileWriter writer;
	
	//////////////////////////////////////////////////////////////////
	//						SETUP METHODS
	//////////////////////////////////////////////////////////////////
	
	@BeforeClass
	public static void setupFile()  {
		try {
			testFile = new File(FILE_PATH);
			testFile.createNewFile();
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@AfterClass
	public static void destroyFile() throws IOException {
		fileReader.close();
		testFile.delete();
	}
	
	@Before
	public void setup() {
		try {
			writer = new FileWriter(testFile);
			writer.write("2020-11-22, ");
			writer.append("12:32:58, ");
			writer.append("2523, ");
			writer.append("rpm");
			
			writer.append("\n");
	
			writer.append("2020-11-23, ");
			writer.append("12:32:58, ");
			writer.append("24, ");
			writer.append("speed");
			
			writer.append("\n");
	
			writer.append("2020-12-18, ");
			writer.append("12:32:58, ");
			writer.append("12.54901961, ");
			writer.append("throttle position");
			
			writer.flush();
			writer.close();
			
			fileReader = new LogFileReader(FILE_PATH);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	
	@Test
	public void test_reading_first_line() throws IOException {
		String result = fileReader.getNextLine();
		
		assertEquals("2020-11-22, 12:32:58, 2523, rpm", result);
		assertNotEquals(null, result);
	}
	
	@Test
	public void test_read_first_line_and_return_DataLog_object() throws IOException {
		DataLog result = fileReader.returnLineAsObject();
		System.out.println(result);
		
		LocalDate testDate = LocalDate.parse("2020-11-22");
		LocalTime testTime = LocalTime.parse("12:32:58");
		
		assertEquals("Date was not correct.  Expected: " + testDate.toString()
					, testDate, result.getDate());
		assertEquals("Time was not correct.  Expected: " + testTime.toString()
					, testTime, result.getTime());
		assertEquals("Value was not correct.  Expected 2523", 2523, result.getValue(), 0.01);
		assertEquals("Data Type not correct.  Expected rpm", "rpm", result.getDataType());
	}
}
