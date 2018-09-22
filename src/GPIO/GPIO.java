package GPIO;

import java.io.*;

public class GPIO {

    public static int OUT = 1;
    public static int IN = 0;

    private int typ = -1;
    private int pin = -1;

    private File path;

    private File exportFile;
    private File unexportFile;

    public GPIO() throws FileNotFoundException {
        this.init();
    }

    public GPIO(int pin) throws FileNotFoundException {
        this.pin = pin;
        this.init();
    }

    public GPIO(int pin, int typ) throws FileNotFoundException {
        this.pin = pin;
        this.typ = typ;
        this.init();
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public void setTyp(int typ) {
        this.typ = typ;
    }

    private void init() throws FileNotFoundException {
        this.path = new File("/sys/class/gpio");
        this.exportFile  = new File(this.path.getAbsolutePath() + "/exportFile");
        this.unexportFile  = new File(this.path.getAbsolutePath() + "/unexportFile");

        if(!this.path.exists()) {
           throw new FileNotFoundException("path not found");
        } else if(!this.exportFile.exists()) {
           throw new FileNotFoundException("export file not found");
        } else if(!this.unexportFile.exists()) {
           throw new FileNotFoundException("unxport file not found");
        }
    }
    
    public boolean export() {
        File gpioFile = new File(this.path.getAbsolutePath() + "/gpio" + String.valueOf(this.pin));
        if(this.pin != -1 && this.typ !=  -1 && !gpioFile.exists()) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.exportFile, true));
                bufferedWriter.append(String.valueOf(this.pin));
                bufferedWriter.flush();
                bufferedWriter.close();

                if(!this.writeTyp(gpioFile)) {
                    return false;
                }

            } catch (IOException e) {
                return false;
            }
            return true;
        } else if (this.pin != -1 && this.typ !=  -1 && gpioFile.exists()){
            return this.writeTyp(gpioFile);
        } else {
            return false;
        }
    }

    public boolean unexport() {
        if(this.pin != -1) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.unexportFile, true));
                bufferedWriter.append(String.valueOf(this.pin));
                bufferedWriter.flush();
                bufferedWriter.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean writeTyp(File file) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getAbsolutePath() + "/direction"));
            bufferedWriter.write(String.valueOf(this.typ));
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public boolean isHigh() {
        if(this.pin != -1) {
           File value = new File(this.path.getAbsolutePath() + "/gpio" + this.pin + "/value");
           try {
               BufferedReader bufferedReader = new BufferedReader(new FileReader(value));
               String code = bufferedReader.readLine();
               if(code != null) {
                   if(code.equals("1")) {
                       return true;
                   } else if(code.equals("0")) {
                       return false;
                   }
               }
           } catch (IOException e) {
               e.printStackTrace();
           }
        }
        return false;
    }
}
