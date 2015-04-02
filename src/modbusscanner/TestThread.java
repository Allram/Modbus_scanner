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


public  class TestThread implements Runnable
{
    Thread t;
	int Count;
    boolean startCounter = false;
    String sVal = "COM1 : ";

	//public void init()
//	{
//		Count=0;
//		t=new Thread(this);
//		t.start();
//	}
public void run()
	{
    
		while(Count < 100)
		{
            if (startCounter){
			Count++;
            System.out.println(sVal + Integer.toString(Count));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {System.out.println("sleep error");}
		}
        }
	}
public void stop(){

t.stop();
}
}
