import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import com.fazecast.jSerialComm.SerialPort;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ConstructGUI {
	
	
	static SerialPort chosenPort;
	static int x = 0;
	static int counter = 0;
	
	ExcelWriter excelWriter;
	

	public void initializeGUI() throws FileNotFoundException, IOException {
		
		excelWriter = new ExcelWriter();
		
		// create and configure the window
		JFrame window = new JFrame();
		window.setTitle("George Mason Baja SAE Telemetry Data Logger");
		window.setLayout(new BorderLayout());
		//sets default maximum size
		window.setSize( (int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width), 
				(int) (GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height ));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create a drop-down box and connect button, then place them at the top of the window
		JComboBox<String> portList = new JComboBox<String>();
		JButton connectButton = new JButton("Connect");
		JPanel topPanel = new JPanel();
		topPanel.add(portList);
		topPanel.add(connectButton);
		window.add(topPanel, BorderLayout.NORTH);
		
		// populate the drop-down box
		SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());

		// create the line graph
		XYSeries series = new XYSeries("Speedometer Readings");
		XYSeriesCollection dataset = new XYSeriesCollection(series);
		JFreeChart chart = ChartFactory.createXYLineChart("Speedometer Readings", "Time (seconds)", "ADC Reading", dataset);

		// configure the connect button and use another thread to listen for data
		connectButton.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0) {
				if(connectButton.getText().equals("Connect")) {
					
					// attempt to connect to the serial port
					chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if(chosenPort.openPort()) {
						connectButton.setText("Disconnect");
						portList.setEnabled(false);

						//initilizes Speedometer chart
						Speedometer speedometer = new Speedometer(chosenPort);
						window.add(speedometer, BorderLayout.CENTER);
						
						//FuelGuage fuelGuage = new FuelGuage(chosenPort);
					//	window.add(fuelGuage, BorderLayout.SOUTH);
					}

				} 
				else {
					// disconnect from the serial port
					chosenPort.closePort();
					portList.setEnabled(true);
					connectButton.setText("Connect");
					series.clear();
					x = 0;
				}	
			}
		});
		
		window.setVisible(true);  // show the window
	}

}





