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


public class ModbusFrames {
    public byte modbusOutFrame[] = new byte[16];
    public boolean exceptionResponse;
    public boolean wrongCRC;
    public boolean wrongDataLen;
    // public short modbusInFrame[] = new short[524];
    public int frameLength;
    public void setFrame(int slaveAdd,int functionNr,int startAdd, int dataLength){
        int modbusCRC16[] = new int[2];
        modbusOutFrame[0] = (byte)slaveAdd;
        modbusOutFrame[1] = (byte)functionNr;
        modbusOutFrame[2] = (byte)(startAdd >> 8);
        modbusOutFrame[3] = (byte)((startAdd << 8)>>8);
        modbusOutFrame[4] = (byte)(dataLength >> 8);
        modbusOutFrame[5] = (byte)((dataLength << 8)>>8);
        modbusCRC16 = CRC16.getCRC(modbusOutFrame, 6);
        modbusOutFrame[6] = (byte)((modbusCRC16[0]<<8)>>8);
        modbusOutFrame[7] = (byte)((modbusCRC16[1]<<8)>>8);
        frameLength = 8;
    }
    public void checkFrame(int functionNr,int dataLength, int frameLen,byte[] frame){
    int modbusCRC16[] = new int[2];
    int dataLen = 0;

    exceptionResponse = false;
    wrongCRC = false;
    wrongDataLen = false;
        if ((frameLen == 5) & (frame[1] == (byte)(0x80 + functionNr)) )
            exceptionResponse = true;


        else
        {
            dataLen = dataLength * 2;
            if ((functionNr == 1)& (functionNr == 2)){
              dataLen = dataLength / 8;
              if ((dataLength % 8)>0)
                 dataLen++;
            }
        }
        if (frameLen != (dataLen +5))
            wrongDataLen = true;
        else {
        modbusCRC16 = CRC16.getCRC(frame, frameLen - 2);
        if (((byte)((modbusCRC16[0]<<8)>>8) != frame[frameLen - 2]) | ((byte)((modbusCRC16[1]<<8)>>8) != frame[frameLen - 1]))
            wrongCRC = true;

        }
        if (exceptionResponse)
           ModbusScannerView.setNewLine(" : Exception  nr " + Integer.toHexString(frame[2]) + " - frame : ");
    else if (wrongCRC)
           ModbusScannerView.setNewLine(" : Erron in CRC checksum - frame : " );
    else if (wrongDataLen)
           ModbusScannerView.setNewLine(" : Wrong length of response - frame : " );
    else
           ModbusScannerView.setNewLine(" : Correct response - frame : " + Integer.toHexString(frame[2]));
    for(dataLen = 0; dataLen<frameLen; dataLen++)

    ModbusScannerView.setNewLine("[" + Integer.toHexString(0x000000FF & (int)frame[dataLen])+"]");

    ModbusScannerView.setNewLine("\n");

    }

}
