package entity;

public class Ingredient {
    private int id;
    private long ean;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getEan() {
        return ean;
    }

    public void setEan(long ean) {
        this.ean = ean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
