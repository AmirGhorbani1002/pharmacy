package util.list;

import java.util.Arrays;

public class MyList<T> {

    private T[] items = (T[]) new Object[1000];
    private int index = 0;

    public void add(T t) {
        if (index > items.length - 1) {
            items = Arrays.copyOf(items, index * 2);
        }
        items[index++] = t;
    }

    public void remove(int id) {
        items[id] = null;
        if (index - id >= 0) System.arraycopy(items, id + 1, items, id, index - id);
    }

    public T[] getList() {
        return items;
    }

    public void setItems(T[] items) {
        this.items = items;
        for (T t : items) {
            if (t != null)
                index++;
        }
    }

    public T getItem(int i){
        return items[i];
    }

    public int size() {
        return index;
    }

    @Override
    public String toString() {
        String temp = "";
        for(int i=0;i<index;i++){
            if(i == index - 1)
                temp += items[i];
            else
                temp += items[i] + "\n";
        }
        return temp;
    }
}
