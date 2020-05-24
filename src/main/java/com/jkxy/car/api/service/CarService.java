package com.jkxy.car.api.service;

import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.pojo.Inventory;

import java.util.List;


public interface CarService {

    List<Car> findAll();

    Car findById(int id);

    List<Car> findByCarName(String carName);

    void deleteById(int id);

    void updateById(Car car);

    void insertCar(Car car);

    void buyCar(Inventory inventory);

    Inventory findCarInventory(int id);

    void insertCarInventory(Inventory inventory);

    void updateCarInventory(Inventory inventory);

    List<Car> findCarsByName(String carName, int offset, int rows);
}
