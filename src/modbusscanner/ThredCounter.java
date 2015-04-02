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


public class ThredCounter extends TestThread {
boolean start = false;
boolean stop = true;
public void startThread(){
    this.run();
    start = true;
    stop = false;
}
public void waitThread(){
    try {
        this.wait();
    } catch (InterruptedException e){}
    start = false;
    stop = true;
}

}
