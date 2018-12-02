package entity;

public class Recipe {
    private int id;
    private int drinkFk;
    private String ean;
    private float columeCl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDrinkFk() {
        return drinkFk;
    }

    public void setDrinkFk(int drinkFk) {
        this.drinkFk = drinkFk;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public float getColumeCl() {
        return columeCl;
    }

    public void setColumeCl(float columeCl) {
        this.columeCl = columeCl;
    }
}
