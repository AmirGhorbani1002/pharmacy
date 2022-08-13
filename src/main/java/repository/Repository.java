package repository;

public interface Repository<T> {

    void save(T t);
    void remove(long id);
    T load(long id);
    void update(T t);

}
