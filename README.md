# OBDPi_Logger
# Author: James Reese
Python script to read and write data from OBD-II to CSV using python-OBD library

This project relies on the [python-OBD library](https://github.com/brendan-w/python-OBD/releases)
Tested on version 0.7.1 in a Hyundai Veloster - 2016

At the moment this project reads data from the On Board Diagnostic (OBD-II) port on a car,
  conected to a Raspberry Pi.
Uses ASync comands to watch for updates from certain PIDs and writes them to a csv file 
  when it receives a new value.
Can read through csv file and POST to a database using API calls.  
  
  Future plans include:
  -Watching for more data
    -currently records RPM, Speed, and Throttle Position
    -would like to add, boost pressure, oil temp, fuel pressure
        -Will need to find Hyundai Specific extended PIDs to support above params
  -Streaming data live to an android phone for viewing while in car (Digital Gauges)
