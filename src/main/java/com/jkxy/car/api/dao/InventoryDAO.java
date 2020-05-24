package com.jkxy.car.api.dao;

import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.pojo.Inventory;
import org.apache.ibatis.annotations.*;

@Mapper
public interface InventoryDAO {

    @Select("select * from carInventory where id = #{id}")
    Inventory findCarInventory(int id);

    @Update("update carInventory set quantity=#{quantity}, checkKey=#{checkKey} where id=#{id}")
    void updateCarInventory(Inventory inventory);

    @Insert("insert into carInventory(id,quantity) values(#{id},#{quantity})")
    void insertCarInventory(Inventory inventory);

    @Update("update carInventory set checkKey=#{checkKey} where id=#{id} and checkKey is null")
    void lockCar(Inventory inventory);

    @Update("update carInventory set quantity=quantity-#{quantity}, checkKey=null where id=#{id} and checkKey=#{checkKey}")
    void buyCar(Inventory inventory);
}
