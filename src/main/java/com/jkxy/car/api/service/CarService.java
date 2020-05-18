package com.jkxy.car.api.service;

import com.jkxy.car.api.pojo.Car;

import java.util.List;


public interface CarService {

    List<Car> findAll();

    Car findById(int id);

    List<Car> findByCarName(String carName);

    void deleteById(int id);

    void updateById(Car car);

    void insertCar(Car car);

    void buyCar(int id, String checkKey);

    void lockCar(int id, String checkKey);

    List<Car> findCarsByName(String carName, int offset, int rows);
}
