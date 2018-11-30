package entity;

public class Inventory {
    private int id;
    private int position;
    private long ean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public long getEan() {
        return ean;
    }

    public void setEan(long ean) {
        this.ean = ean;
    }
}
