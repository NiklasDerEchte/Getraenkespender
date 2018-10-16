package entity;

public class Drink {
    private int id;
    private String name;
    private String description;
    private float pos1;
    private float pos2;
    private float pos3;
    private float pos4;
    private float pos5;

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

    public float getPos1() {
        return pos1;
    }

    public void setPos1(float pos1) {
        this.pos1 = pos1;
    }

    public float getPos2() {
        return pos2;
    }

    public void setPos2(float pos2) {
        this.pos2 = pos2;
    }

    public float getPos3() {
        return pos3;
    }

    public void setPos3(float pos3) {
        this.pos3 = pos3;
    }

    public float getPos4() {
        return pos4;
    }

    public void setPos4(float pos4) {
        this.pos4 = pos4;
    }

    public float getPos5() {
        return pos5;
    }

    public void setPos5(float pos5) {
        this.pos5 = pos5;
    }

    public float[] getPosArray() {
        return new float[]{getPos1(), getPos2(), getPos3(), getPos4(), getPos5()};
    }
}
