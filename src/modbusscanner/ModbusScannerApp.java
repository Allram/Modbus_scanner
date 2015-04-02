/***
 * Java Modbus Scanner
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

import org.jdesktop.application.Application;

import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 */
public class ModbusScannerApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new ModbusScannerView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of ModbusScannerApp
     */
    public static ModbusScannerApp getApplication() {
        return Application.getInstance(ModbusScannerApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(ModbusScannerApp.class, args);
    }
}
