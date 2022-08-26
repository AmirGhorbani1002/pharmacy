package entity;

public class SimpleDrug { //todo DrugDTO

    private final String name;
    private int count;

    public SimpleDrug(String name, int count) {
        this.name = name;
        this.count = count;
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

    @Override
    public String toString() {
        return  "name='" + name + '\'' +
                ", count=" + count;
    }
}
