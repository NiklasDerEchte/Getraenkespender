package bin;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

import java.io.IOException;

public class LCD {

    private static final int LCD_CLEARDISPLAY = 0x01;
    private static final int LCD_RETURNHOME = 0x02;
    private static final int LCD_ENTRYMODESET = 0x04;
    private static final int LCD_DISPLAYCONTROL = 0x08;
    private static final int LCD_CURSORSHIFT = 0x10;
    private static final int LCD_FUNCTIONSET = 0x20;
    private static final int LCD_SETCGRAMADDR = 0x40;
    private static final int LCD_SETDDRAMADDR = 0x80;
    private static final int LCD_ENTRYRIGHT = 0x00;
    private static final int LCD_ENTRYLEFT = 0x02;
    private static final int LCD_ENTRYSHIFTINCREMENT = 0x01;
    private static final int LCD_ENTRYSHIFTDECREMENT = 0x00;
    private static final int LCD_DISPLAYON = 0x04;
    private static final int LCD_DISPLAYOFF = 0x00;
    private static final int LCD_CURSORON = 0x02;
    private static final int LCD_CURSOROFF = 0x00;
    private static final int LCD_BLINKON = 0x01;
    private static final int LCD_BLINKOFF = 0x00;
    private static final int LCD_DISPLAYMOVE = 0x08;
    private static final int LCD_CURSORMOVE = 0x00;
    private static final int LCD_MOVERIGHT = 0x04;
    private static final int LCD_MOVELEFT = 0x00;
    private static final int LCD_8BITMODE = 0x10;
    private static final int LCD_4BITMODE = 0x00;
    private static final int LCD_2LINE = 0x08;
    private static final int LCD_1LINE = 0x00;
    private static final int LCD_5x10DOTS = 0x04;
    private static final int LCD_5x8DOTS = 0x00;
    private static final int LOW = 0x0;
    private static final int PCF_RS = 0x01;
    private static final int PCF_RW = 0x02;
    private static final int PCF_EN = 0x04;
    private static final int PCF_BACKLIGHT = 0x08;
    private static final int RSMODE_CMD = 0;
    private static final int RSMODE_DATA = 1;
    private boolean backlight;
    private byte displayFunction;
    private byte displayControl;
    private byte displayMode;

    private int numLines;

    private I2CDevice device;

    public LCD(int i2cAddress, int col, int row) throws IOException {
        try {
            device = I2CFactory.getInstance(I2CBus.BUS_1).getDevice(i2cAddress);
            init(col, row, LCD_5x8DOTS);
        } catch (I2CFactory.UnsupportedBusNumberException e) {
            e.printStackTrace();
        }
    }

    private void init(int cols, int rows, int charsize) throws IOException {
        numLines = rows;
        displayFunction = 0;
        if (rows > 1) {
            displayFunction |= LCD_2LINE;
        }

        if ((charsize != 0) && (rows == 1)) {
            displayFunction |= LCD_5x10DOTS;
        }
        write2Wire((byte) 0x00, LOW, false);
        delayMicroseconds(50000);
        sendNibble((byte) 0x03, RSMODE_CMD);
        delayMicroseconds(4500);
        sendNibble((byte) 0x03, RSMODE_CMD);
        delayMicroseconds(4500);
        sendNibble((byte) 0x03, RSMODE_CMD);
        delayMicroseconds(150);
        sendNibble((byte) 0x02, RSMODE_CMD);
        command(LCD_FUNCTIONSET | displayFunction);
        displayControl = LCD_DISPLAYON | LCD_CURSOROFF | LCD_BLINKOFF;
        setEnableDisplay(true);
        clear();
        displayMode = LCD_ENTRYLEFT | LCD_ENTRYSHIFTDECREMENT;
        command(LCD_ENTRYMODESET | displayMode);
    }

    public void clear() throws IOException {
        command(LCD_CLEARDISPLAY);
        delayMicroseconds(2000);
    }

    public void home() throws IOException {
        command(LCD_RETURNHOME);
        delayMicroseconds(2000);
    }

    public void setCursorPosition(int col, int row) throws IOException {
        int row_offsets[] = {0x00, 0x40, 0x14, 0x54};
        if (row >= numLines) {
            row = numLines - 1;
        }
        command(LCD_SETDDRAMADDR | (col + row_offsets[row]));
    }

    public void setEnableDisplay(boolean arg) throws IOException {
        if(arg) {
            displayControl |= LCD_DISPLAYON;
            command(LCD_DISPLAYCONTROL | displayControl);
        } else {
            displayControl &= ~LCD_DISPLAYON;
            command(LCD_DISPLAYCONTROL | displayControl);
        }
    }

    public void setBlinkCursor(boolean arg) throws IOException {
        if(arg) {
            displayControl |= LCD_BLINKON;
            command(LCD_DISPLAYCONTROL | displayControl);
        } else {
            displayControl &= ~LCD_BLINKON;
            command(LCD_DISPLAYCONTROL | displayControl);
        }
    }

    public void setEnableCursor(boolean arg) throws IOException {
        if(arg) {
            displayControl |= LCD_CURSORON;
            command(LCD_DISPLAYCONTROL | displayControl);
        } else {
            displayControl &= ~LCD_CURSORON;
            command(LCD_DISPLAYCONTROL | displayControl);
        }
    }

    public void scrollDisplayLeft() throws IOException {
        command(LCD_CURSORSHIFT | LCD_DISPLAYMOVE | LCD_MOVELEFT);
    }

    public void scrollDisplayRight() throws IOException {
        command(LCD_CURSORSHIFT | LCD_DISPLAYMOVE | LCD_MOVERIGHT);
    }


    public void leftToRight() throws IOException {
        displayMode |= LCD_ENTRYLEFT;
        command(LCD_ENTRYMODESET | displayMode);
    }

    public void rightToLeft() throws IOException {
        displayMode &= ~LCD_ENTRYLEFT;
        command(LCD_ENTRYMODESET | displayMode);
    }

    public void setAutoscroll(boolean arg) throws IOException {
        if(arg) {
            displayMode |= LCD_ENTRYSHIFTINCREMENT;
            command(LCD_ENTRYMODESET | displayMode);
        } else {
            displayMode &= ~LCD_ENTRYSHIFTINCREMENT;
            command(LCD_ENTRYMODESET | displayMode);
        }
    }

    public void setBacklight(boolean enable) throws IOException {
        backlight = enable;
        write2Wire((byte) 0x00, RSMODE_DATA, false);
    }

    public void setCustomChar(int location, int[] charmap) throws IOException {
        location &= 0x7;
        command(LCD_SETCGRAMADDR | (location << 3));
        for (int i = 0; i < 8; i++) {
            write(charmap[i]);
        }
    }

    private void write(int value) throws IOException {
        send(value, RSMODE_DATA);
    }

    public void print(String message) throws IOException {
        for (int i = 0; i < message.length(); i++) {
            write(message.charAt(i));
        }
    }

    private void command(int value) throws IOException {
        send(value, RSMODE_CMD);
    }

    private void send(int value, int mode) throws IOException {
        byte valueLo = (byte) (value & 0x0F);
        byte valueHi = (byte) (value >> 4 & 0x0F);
        sendNibble(valueHi, mode);
        sendNibble(valueLo, mode);
    }

    private void sendNibble(byte halfByte, int mode) throws IOException {
        write2Wire(halfByte, mode, true);
        delayMicroseconds(1);
        write2Wire(halfByte, mode, false);
        delayMicroseconds(37);
    }

    private void write2Wire(byte halfByte, int mode, boolean enable) throws IOException {
        byte i2cData = (byte) (halfByte << 4);
        if (mode > 0) i2cData |= PCF_RS;
        if (enable) i2cData |= PCF_EN;
        if (backlight) i2cData |= PCF_BACKLIGHT;

        device.write(1,new byte[]{i2cData});
    }

    private void delayMicroseconds(long microseconds) {
        try {
            Thread.sleep(Math.max(1, Math.round(0.001d * microseconds)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
