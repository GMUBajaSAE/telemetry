/*
George Mason Telemetry Electrical Team
Modified from Arvind Sanjeev
*/

 #define TIRE_RADIUS 2
 #define IN_PER_SEC_TO_MPH  0.0568181818  //Multiply by this conversion factor

 volatile byte half_revolutions;
 unsigned int rpm;
 unsigned long timeold;


 void setup()
 {
   Serial.begin(115200);
   attachInterrupt(0, magnet_detect, RISING);//Initialize the intterrupt pin (Arduino digital pin 2)
   half_revolutions = 0;
   rpm = 0;
   timeold = 0;

   //initializes the LCD Screen
   // set up the LCD's number of columns and rows:

 }
 void loop()//Measure RPM
 {
   if (half_revolutions >= 20) {
     rpm = 30*1000/(millis() - timeold)*half_revolutions;
     timeold = millis();
     half_revolutions = 0;
     //Serial.println(rpm,DEC);
   }
 }

 //This function is called whenever a magnet/interrupt is detected by the arduino
 void magnet_detect()
 {
   int speed = 0;
   half_revolutions++;
   Serial.println("detect");
   speed = measureSpeed();  //gets the speed of the magnetic detection
   Serial.println(speed);


 }

 //Calculates the speed of the magnetic pulses
 //@param returns an integer velocity value
 double measureSpeed(){
    double angularVelocity = 0;
    double frequency = 0;
    double timeCurrent = 0 ;
    double velocity = 0;

     timeCurrent = (double)(millis()- timeold); //gets the period elapsed in milliseconds
     frequency = 1.0/(timeCurrent/1000.0); //converts to frequency in mHz to Hz
     angularVelocity = 2 * PI * frequency;  // w = 2*pi*f  OR 2*pi*time for one rotation
     velocity = TIRE_RADIUS * angularVelocity;   // v = r*w

     timeold = timeCurrent; //updates the time to current time
     Serial.print("frequency: ");
     Serial.println(frequency);
     Serial.print("timeCurrent: ");
     Serial.println(timeCurrent);
     Serial.print("angularVelocity: ");
     Serial.println(angularVelocity);
     Serial.print("velocity: "); 
     Serial.println(velocity);
     Serial.print(velocity * IN_PER_SEC_TO_MPH);
     return velocity * IN_PER_SEC_TO_MPH;
 }

//detect
//frequency: 0.10
//timeCurrent: 9968.00
//angularVelocity: 0.63
//velocity: 1.26
//0.070
//detect
//frequency: 0.62
//timeCurrent: 1606.00
//angularVelocity: 3.91
//velocity: 7.82
//0.440
//detect
//frequency: 0.09
//timeCurrent: 10784.00
//angularVelocity: 0.58
//velocity: 1.17
//0.070
//detect
//frequency: 0.43
//timeCurrent: 2351.00
//angularVelocity: 2.67
//velocity: 5.35
//0.300
//detect
//frequency: 0.09
//timeCurrent: 11601.00
//angularVelocity: 0.54
//velocity: 1.08
//0.060
