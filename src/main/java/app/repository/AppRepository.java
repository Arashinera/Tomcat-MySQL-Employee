package app.repository;

import java.util.List;

public interface AppRepository<T> {
    void create(T obj);
    List<T> read();
    T readById(Long id);
    void update(T obj);
    void delete(Long id);
}
