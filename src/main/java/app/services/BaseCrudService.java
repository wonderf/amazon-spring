package app.services;

import java.util.List;
import java.util.Optional;

public interface BaseCrudService<T> {
    T save(T item);
    T update(T item);
    Optional<T> findById(Long id);
    List<T> findAll();
    void deleteById(Long id);
}
