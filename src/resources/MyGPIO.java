package resources;

import java.io.*;

public class MyGPIO {

    public static String OUT = "out";
    public static String IN = "in";
    public static int EXCEPTION_CODE = -1;
    public static int HIGH_CODE = 1;
    public static int LOW_CODE = 0;

    private String typ = "";
    private int pin = -1;
    private int initValue;

    private File path;

    private File exportFile;
    private File unexportFile;

    public MyGPIO(int pin, String typ) {
        this(pin, typ, -1);
    }

    public MyGPIO(int pin, String typ, int initValue) {
        this.pin = pin;
        this.typ = typ;
        this.initValue = initValue;
        this.init();
    }

    private void init() {
        this.path = new File("/sys/class/gpio");
        this.exportFile  = new File(this.path.getAbsolutePath() + "/export");
        this.unexportFile  = new File(this.path.getAbsolutePath() + "/unexport");
    }

    public int invertCode(int code) {
        if(code == MyGPIO.HIGH_CODE) {
            return MyGPIO.LOW_CODE;
        } else if(code == MyGPIO.LOW_CODE) {
            return MyGPIO.HIGH_CODE;
        }
        return -1;
    }
    
    public int export() {
        File gpioFile = new File(this.path.getAbsolutePath() + "/gpio" + String.valueOf(this.pin));
        if(this.pin != -1 && !this.typ.trim().isEmpty() && !gpioFile.exists()) {
            try {
                FileWriter fileWriter = new FileWriter(this.exportFile, true);
                fileWriter.append(String.valueOf(this.pin));
                fileWriter.flush();
                fileWriter.close();
                int ret = this.writeTyp(gpioFile);
                this.initValue();
                return ret;
            } catch (IOException e) {
                return -1;
            }
        } else if (this.pin != -1 && !this.typ.trim().isEmpty() && gpioFile.exists()){
            int ret = this.writeTyp(gpioFile);
            this.initValue();
            return ret;
        } else {
            return 0;
        }
    }

    private void initValue() {
        if(this.initValue == 0 || this.initValue == 1) {
            this.setValue(this.initValue);
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
           File value = new File(this.path.getAbsolutePath() + "/gpio" + this.pin + "/value");
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
            File value = new File(this.path.getAbsolutePath() + "/gpio" + this.pin + "/value");
            try {
                FileWriter fileWriter = new FileWriter(value);
                if(code == MyGPIO.HIGH_CODE || code == MyGPIO.LOW_CODE) {
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
