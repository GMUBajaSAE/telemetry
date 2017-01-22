# Front-End Telemetry GUI
The telemetry GUI applications complements the embedded development of the sensor suite encompassed in the George Mason University's BAJA SAE offroad car.  

#Libraries
The GUI application is written in Java utilizing Swing as the main GUI library.  
- [Apahe POI](https://poi.apache.org/) library to format and export real time data written directly onto an excel spreadsheet wit .xls . 
- [JserialComm](http://fazecast.github.io/jSerialComm/) library is used to communicate between AVR code written in C a the Java front-end iterface.  
- [JFreeChart](http://www.jfree.org/jfreechart/) is used as the main graphics library for chart formatting. 

#To Do
- Incorperate Hashmap caching to optimize the refresh rate of the serial communication
- Switch to openGl framework for hardware acceleration
- Resizable, customizable interface
