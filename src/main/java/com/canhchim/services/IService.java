package com.canhchim.services;

import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IService<T> {

    Optional<T> findById(Long id);

    Page<T> findAll(int page, int size);

    Page<T> searchByName(String search, int page, int size);

    T save(T t);

    void remove(T t);

}
