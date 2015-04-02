/***
 * Java Modbus Scanner
 * Copyright (c) 2015, Vegard Fladby
 * vegard@fladby.org - www.fladby.org
 * All rights reserved.
 *
 *
 *  This software is free you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *  In no event shall the regents or contributors be liable for any direct,
 *  indirect, incidential, special, exemplary, or consequential damages.
 *
 */
package modbusscanner;
import gnu.io.*;
import java.io.*;
import java.util.*;

public class ModbusRTU  implements SerialPortEventListener {
 static Enumeration portList;
 static CommPortIdentifier portID;
 SerialPort RTUMaster;
 int offset = 0;
 static InputStream           inputStream;
 byte[] readBuffer = new byte[524];
//CommPort CommMaster;
static boolean outputBufferEmptyFlag = false;
static OutputStream outputStream;
public void closePort(){
    RTUMaster.close();
}

public  void writeToPort(byte[] frame,int len ){
    offset = 0;
    try {
    outputStream.write(frame, 0, len);
    }catch (IOException e){System.out.println("Write to port error : " + e.toString());}
}

public void openPort(String portName){
    portList = CommPortIdentifier.getPortIdentifiers();
    portID = (CommPortIdentifier) portList.nextElement();
    while ((portList.hasMoreElements()) & !(portID.getName().equals(portName))){
        portID = (CommPortIdentifier) portList.nextElement();
    }
    try {
        RTUMaster = (SerialPort)portID.open("SimpleWrite",2000);
    } catch (PortInUseException e){
    ScannerThread.startTest = false;
    ModbusScannerView.setButtonText("Start Scann");
    ModbusScannerView.showDialog(portName + " in use !!!");
    }
    try {
        outputStream = RTUMaster.getOutputStream();
    } catch (IOException e){System.out.println("Error get output stream : " + e.toString());}
 try {
         inputStream = RTUMaster.getInputStream();
      } catch (IOException e) {}

      try {
         RTUMaster.addEventListener(this);
      } catch (TooManyListenersException e) {}


      // activate the DATA_AVAILABLE notifier
      RTUMaster.notifyOnDataAvailable(true);
}

public  void setCommPort(String CommPortName,int baudRate, int dataBits, int stopBits, int parity){
openPort(CommPortName);
    try{
RTUMaster.setSerialPortParams(baudRate, dataBits, stopBits, parity);
}
    catch(UnsupportedCommOperationException e){
        System.out.println("Comm Port settings Error"+e.getMessage());
    }

try {
    RTUMaster.notifyOnOutputEmpty(true);
} catch(Exception e){
    System.out.println("Error setting event notification : " + e.toString());
}
}
public void serialEvent(SerialPortEvent event) {
      int i;
      switch (event.getEventType()) {
      case SerialPortEvent.BI:
      case SerialPortEvent.OE:
      case SerialPortEvent.FE:
      case SerialPortEvent.PE:
      case SerialPortEvent.CD:
      case SerialPortEvent.CTS:
      case SerialPortEvent.DSR:
      case SerialPortEvent.RI:
      case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
         break;
      case SerialPortEvent.DATA_AVAILABLE:
         // we get here if data has been received
        byte[] rBuffer = new byte[524];
         int numBytes = 0;
         try {
            // read data
            while (inputStream.available() > 0) {
                numBytes = inputStream.read(rBuffer);
                for (i=offset; i<numBytes+offset; i++)
                    readBuffer[i] = rBuffer[i-offset];
                offset = offset + numBytes;
            }
            // print data
            //String result  = new String(readBuffer);

            System.out.println("Read from serial port: "+Integer.toString(offset));
            for (i=0; i<numBytes; i++)
            System.out.println(Integer.toHexString(rBuffer[i]));
         } catch (IOException e) {}

         break;
      }
   }

}