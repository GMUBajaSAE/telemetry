var serialport = require("serialport");
var SerialPort = serialport;


var serialport = new SerialPort("/dev/ttyACM0", {
  baudrate: 19200

});



// creates a web server linked to index.html page
//run http://127.0.0.1:8888
var http = require('http'),
    fs = require('fs');


fs.readFile('./index.html', function (err, html) {
    if (err) {
        throw err;
    }
    http.createServer(function(request, response) {
        response.writeHeader(200, {"Content-Type": "text/html"});
        response.write(html);
        response.end();
    }).listen(8888, '127.0.0.1');
});




serialport.on('open', function(){
  console.log('Serial Port Opend');
  serialport.on('data', function(data){
      console.log(data[0]);
  });
});
