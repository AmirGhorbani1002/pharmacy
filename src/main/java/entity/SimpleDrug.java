package entity;

public class SimpleDrug {

    private final long id;
    private final String name;
    private int count;

    public SimpleDrug(long id, String name, int count) {
        this.id = id;
        this.name = name;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
