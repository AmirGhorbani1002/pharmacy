package util.list;

import java.util.Arrays;

public class MyList<T> {

    private T[] items = (T[]) new Object[1000];
    private int index;
    public void add(T t) {
        if (index > items.length - 1) {
            items = Arrays.copyOf(items, index * 2);
        }
        items[index] = t;
        index++;
    }

    public void remove(int id) {
        items[id] = null;
        if (index - id >= 0) System.arraycopy(items, id + 1, items, id, index - id);
    }

    public T[] getList() {
        return items;
    }

    public void setDrugs(T[] items) {
        this.items = items;
    }

    public int getIndex() {
        return index;
    }

}
