package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface DishRepository extends CrudRepository<Dish,Long>{
    Iterable <Dish> findAllByNameContainingIgnoreCase(String search);
}