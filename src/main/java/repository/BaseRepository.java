package repository;

public interface BaseRepository<T> {

    void save(T t);
    void remove(long id);
    T load(long id);
    void update(T t);

}
