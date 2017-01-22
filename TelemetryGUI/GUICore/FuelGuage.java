import java.awt.BorderLayout;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import com.fazecast.jSerialComm.SerialPort;


@SuppressWarnings("serial")
public class FuelGuage extends JPanel{
	
	protected JFrame window;
	protected JButton connectButton;
	protected JComboBox<String> portList;
	protected JPanel topPanel;
	
	static int x = 0;
	static int counter = 0;  //for keeping track of data lines
	//ExcelWriter excelWriter = new ExcelWriter();
	
	
	//Speedometer constructor initalizes speedometer
	public FuelGuage(SerialPort chosenPort){

		super();  //initializes super class constructor
		
		// create the line graph
		XYSeries series = new XYSeries("Speedometer Readings");
		XYSeriesCollection dataset = new XYSeriesCollection(series);
		JFreeChart chart = ChartFactory.createXYLineChart("Speedometer Readings", "Time (seconds)", "ADC Reading", dataset);

		
		
		add(new ChartPanel(chart), BorderLayout.CENTER);
		
					
		// create a new thread that listens for incoming text and populates the graph
		Thread thread = new Thread(){
			@Override public void run() {
				Scanner scanner = new Scanner(chosenPort.getInputStream());
				while(scanner.hasNextLine()) {
					try {
									
						String line = scanner.nextLine();
						int number = Integer.parseInt(line);
				
					//	excelWriter.addData(number); //writes the data to excel stream
						series.add(x++, 1023 - number);
								
						
						counter++;  //increments the maximum x value on graph
			
						//for every 150 cycles, update the y axis
						if(counter%150 == 0){
							chart.getXYPlot().getRangeAxis().setRange(number+100, number+350);
						}
						else{
						//Repaints the Graph as long as Serial communication is true
						//if counter less than 1000, remain still , also gives buffer for noise interference
							if(counter < 1000 && counter > 5){
								chart.getXYPlot().getDomainAxis().setRange(0, 1000);
							}
							//change x position of graph based on data stream
							else{
								chart.getXYPlot().getDomainAxis().setRange(counter - 1000, counter+100);
							}										
				        }	
						
						window.repaint();
						
				  } catch(Exception e) {}
		    	}
			scanner.close();
		}
	   };
	   
	   //starts the thread
		thread.start();
	}
}
