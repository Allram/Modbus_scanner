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


public class ScannerThread implements Runnable
{
    Thread t;
    InputStream           inputStream;

     ModbusFrames MasterFrame = new ModbusFrames();
     ModbusRTU modbusRTU = new ModbusRTU();
	int Count;
    boolean startCounter = false;
    public String commPortName ="";
    public int startBaud = 9600;
    public int endBaud = 9600;
    public boolean parityEven = false;
    public boolean parityOdd = false;
    public boolean parityNone = false;
    public boolean oneStopBit = false;
    public boolean twoStopBits = false;
    public int functionNr =3;
    public int startSlaveAddress =1;
    public int endSlaveAddress=1;
    public int timeOut=1000;
    public int delayBetweenPolls=1000;
    public int startDataAddress=1;
    public int dataLength=10;
    public int retries = 3;
    int baudRate = 9600;
    int parity = 0;
    int stopBits = 1;
    int slaveAddress = 1;
    boolean pEven = false;
    boolean pOdd = false;
    boolean pNone = false;
    boolean sOne = false;
    boolean sTwo = false;
    public static boolean startTest = false;



	//public void init()
//	{
//		Count=0;
//		t=new Thread(this);
//		t.start();
//	}
public void run()
	{
    int i;
    String sParity;
    String sStopBits;
    int repeatWait;
    

		while(true)
		{

            try{
            Thread.sleep(10);
            } catch (InterruptedException e) {System.out.println("sleep error");}
            if (startTest){
                
			slaveAddress = startSlaveAddress;
      
            try {
                while ((slaveAddress <= endSlaveAddress)& (startTest)){
                    ModbusScannerView.setNewLine("Start...\n");
                    ModbusScannerView.setNewLine("Searching for slave nr " + Integer.toString(slaveAddress)+ "\n");
                    baudRate = startBaud;
                    while ((baudRate <= endBaud)& (startTest)){
                        pEven = parityEven;
                        pOdd = parityOdd;
                        pNone = parityNone;
                        while ((pNone|pEven|pOdd)& (startTest)){
                            if (pNone){
                                parity = SerialPort.PARITY_NONE;
                                pNone = false;
                                sParity = "NONE";
                            }else if (pEven){
                             parity = SerialPort.PARITY_EVEN;
                                pEven = false;
                                sParity = "EVEN";
                            } else {
                                parity = SerialPort.PARITY_ODD;
                                pOdd = false;
                                sParity = "ODD";
                            }
                            sOne = oneStopBit;
                            sTwo = twoStopBits;
                    while ((sOne | sTwo)& (startTest)){
                        if (sOne){
                            stopBits = SerialPort.STOPBITS_1;
                            sOne = false;
                            sStopBits = "1";
                        } else
                        {
                            stopBits = SerialPort.STOPBITS_2;
                            sTwo = false;
                            sStopBits = "2";
                        }

    


                MasterFrame.setFrame(slaveAddress,functionNr, startDataAddress, dataLength);
                modbusRTU.setCommPort(commPortName, baudRate, SerialPort.DATABITS_8, stopBits, parity);
                modbusRTU.RTUMaster.notifyOnDataAvailable(true);
                modbusRTU.writeToPort((byte[])MasterFrame.modbusOutFrame, MasterFrame.frameLength);
                repeatWait = 0;
                System.out.println("Writing to port");
                while ((repeatWait < timeOut/10 )&(modbusRTU.offset <=0)){
                Thread.sleep(10);
                repeatWait++;
                }
                Thread.sleep(delayBetweenPolls);
                if (modbusRTU.offset >0){
                   ModbusScannerView.setNewLine("Response from slave nr " + Integer.toString(slaveAddress) + "  at [ " + Integer.toString(baudRate) + ", 8, ");
                   ModbusScannerView.setNewLine(sParity + ", " + sStopBits + " ]");

                    MasterFrame.checkFrame(functionNr, dataLength,modbusRTU.offset , modbusRTU.readBuffer);
                   
                }
                   else
                        System.out.println("No data read");
                modbusRTU.closePort();
                        }
                        }
                    baudRate= baudRate*2;
                    }
                slaveAddress++;
                if (slaveAddress > endSlaveAddress) {
                    startTest = false;
                    ModbusScannerView.setNewLine("End of scann ...\n");
                    ModbusScannerView.setButtonText("Start Scann");
                }
                }

			} catch (InterruptedException e) {System.out.println("sleep error");}
		}
            
        }
	}


public void stop(){

t.stop();
}

}
