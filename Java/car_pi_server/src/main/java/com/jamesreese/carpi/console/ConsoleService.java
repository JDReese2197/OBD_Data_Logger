package com.jamesreese.carpi.console;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConsoleService {
	
    public void logApiCall(String message) {
    	LocalDateTime now = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.A");
	    String timeNow = now.format(formatter);
	    System.out.println(timeNow + ": " + message);
    }
}
