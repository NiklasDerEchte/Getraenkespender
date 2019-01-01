package bin;

import java.io.IOException;

public class GUI {

    private LCD mLCD1602;

    public GUI() {
        this.init();
    }

    private void init() {
        try {
            this.mLCD1602 = new LCD(0x27, 16, 2);
            this.mLCD1602.clear();
            this.mLCD1602.setBacklight(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMessage(String text) {
        if(this.mLCD1602 != null) {
            try {
                this.mLCD1602.setCursorPosition(0,0);
                this.mLCD1602.print("                ");
                this.mLCD1602.setCursorPosition(0,0);
                if(text.length() > 16) {
                    text = text.substring(0, 12) + "...";
                }
                this.mLCD1602.print(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void printSubmessage(String text) {
        if(this.mLCD1602 != null) {
            try {
                this.mLCD1602.setCursorPosition(0,1);
                this.mLCD1602.print("                ");
                this.mLCD1602.setCursorPosition(0,1);
                if(text.length() > 16) {
                    text = text.substring(0, 13) + "...";
                }
                this.mLCD1602.print(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void printNextOkMenu() {
        if(this.mLCD1602 != null) {
            try {
                this.mLCD1602.setCursorPosition(0,1);
                this.mLCD1602.print("                ");
                this.mLCD1602.setCursorPosition(0,1);
                this.mLCD1602.print("<- Weiter  OK ->");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void printDrink(String drinkName) {
        this.printMessage(drinkName);
        this.printNextOkMenu();
    }
}
