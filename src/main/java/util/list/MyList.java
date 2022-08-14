package util.list;

public interface MyList<T> {

    void add(T t);
    void remove(int id);

    T[] getList();

}
