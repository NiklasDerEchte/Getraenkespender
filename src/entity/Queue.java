package entity;

public class Queue {
    private int id;
    private int drinkFk;
    private int customDrinkFk;
    private String status;

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

    public int getCustomDrinkFk() {
        return customDrinkFk;
    }

    public void setCustomDrinkFk(int customDrinkFk) {
        this.customDrinkFk = customDrinkFk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
