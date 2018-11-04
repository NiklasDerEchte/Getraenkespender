package entity;

public class Drink {
    private int id;
    private String name;
    private String description;
    private float ingCl1;
    private float ingCl2;
    private float ingCl3;
    private float ingCl4;
    private float ingCl5;
    private int ingFk;

    public int getIngFk() {
        return ingFk;
    }

    public void setIngFk(int ingFk) {
        this.ingFk = ingFk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getingCl1() {
        return ingCl1;
    }

    public void setingCl1(float ingCl1) {
        this.ingCl1 = ingCl1;
    }

    public float getingCl2() {
        return ingCl2;
    }

    public void setingCl2(float ingCl2) {
        this.ingCl2 = ingCl2;
    }

    public float getingCl3() {
        return ingCl3;
    }

    public void setingCl3(float ingCl3) {
        this.ingCl3 = ingCl3;
    }

    public float getingCl4() {
        return ingCl4;
    }

    public void setingCl4(float ingCl4) {
        this.ingCl4 = ingCl4;
    }

    public float getingCl5() {
        return ingCl5;
    }

    public void setingCl5(float ingCl5) {
        this.ingCl5 = ingCl5;
    }

    public float[] getingClArray() {
        return new float[]{getingCl1(), getingCl2(), getingCl3(), getingCl4(), getingCl5()};
    }
}
