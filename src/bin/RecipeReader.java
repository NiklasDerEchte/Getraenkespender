package bin;

import java.io.*;
import java.util.Vector;

public class RecipeReader {

    private File recipeFile;
    private Vector<String[]> data;

    public RecipeReader() {
        this.recipeFile = new File("data/rezepte.csv");
        this.data = new Vector<>();
    }

    private boolean exists() {
        return this.recipeFile.exists();
    }

    private void read() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.recipeFile));
            String line = null;
            bufferedReader.readLine(); // Skip first line
            while ((line = bufferedReader.readLine()) != null) {
                String[] col = line.split(",");
                this.data.add(col);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Vector<String[]> getRecipes() {
        if(this.exists()) {
            this.read();
            return this.data;
        }
        return null;
    }
}
