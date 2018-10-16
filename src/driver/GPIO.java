package driver;

import java.io.*;

public class GPIO {

    public static String OUT = "out";
    public static String IN = "in";
    public static int EXCEPTION_CODE = -1;
    public static int HIGH_CODE = 1;
    public static int LOW_CODE = 0;

    private String typ = "";
    private int pin = -1;

    private File path;

    private File exportFile;
    private File unexportFile;

    public GPIO() {
        this.init();
    }

    public GPIO(int pin) {
        this.pin = pin;
        this.init();
    }

    public GPIO(int pin, String typ) {
        this.pin = pin;
        this.typ = typ;
        this.init();
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    private void init() {
        this.path = new File("/sys/class/bin");
        this.exportFile  = new File(this.path.getAbsolutePath() + "/export");
        this.unexportFile  = new File(this.path.getAbsolutePath() + "/unexport");
    }
    
    public int export() {
        File gpioFile = new File(this.path.getAbsolutePath() + "/bin" + String.valueOf(this.pin));
        if(this.pin != -1 && !this.typ.trim().isEmpty() && !gpioFile.exists()) {
            try {
                FileWriter fileWriter = new FileWriter(this.exportFile, true);
                fileWriter.append(String.valueOf(this.pin));
                fileWriter.flush();
                fileWriter.close();
                return this.writeTyp(gpioFile);
            } catch (IOException e) {
                return -1;
            }
        } else if (this.pin != -1 && !this.typ.trim().isEmpty() && gpioFile.exists()){
            return this.writeTyp(gpioFile);
        } else {
            return 0;
        }
    }

    public int unexport() {
        if(this.pin != -1) {
            try {
                FileWriter fileWriter = new FileWriter(this.unexportFile, true);
                fileWriter.append(String.valueOf(this.pin));
                fileWriter.flush();
                fileWriter.close();
                return 1;
            } catch (IOException e) {
                return -1;
            }
        } else {
            return 0;
        }
    }

    private int writeTyp(File file) {
        try {
            FileWriter fileWriter = new FileWriter(file.getAbsolutePath() + "/direction");
            fileWriter.write(String.valueOf(this.typ));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            return -1;

        }
        return 1;
    }

    public int isHigh() {
        if(this.pin != -1) {
           File value = new File(this.path.getAbsolutePath() + "/bin" + this.pin + "/value");
           try {
               BufferedReader bufferedReader = new BufferedReader(new FileReader(value));
               String code = bufferedReader.readLine();
               if(code != null) {
                   return Integer.parseInt(code);
               }
           } catch (IOException e) {
               return -1;
           }
        }
       return -1;
    }

    public int setValue(int code) {
        if(this.pin != -1) {
            File value = new File(this.path.getAbsolutePath() + "/bin" + this.pin + "/value");
            try {
                FileWriter fileWriter = new FileWriter(value);
                if(code == GPIO.HIGH_CODE || code == GPIO.LOW_CODE) {
                    fileWriter.write(String.valueOf(code));
                    fileWriter.flush();
                    fileWriter.close();
                    return 1;
                }
            } catch (IOException e) {
                return -1;
            }
        }
        return 0;
    }

}
