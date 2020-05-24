package com.jkxy.car.api.controller;

import com.jkxy.car.api.pojo.Car;
import com.jkxy.car.api.pojo.Inventory;
import com.jkxy.car.api.service.CarService;
import com.jkxy.car.api.utils.JSONResult;
import com.jkxy.car.api.utils.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.NestingKind;
import java.util.List;



@RestController
@RequestMapping("car")
public class CarController {
    @Autowired
    private CarService carService;

    /**
     * 查询所有
     *
     * @return
     */
    @GetMapping("findAll")
    public JSONResult findAll() {
        List<Car> cars = carService.findAll();
        return JSONResult.ok(cars);
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @GetMapping("findById/{id}")
    public JSONResult findById(@PathVariable int id) {
        Car car = carService.findById(id);
        return JSONResult.ok(car);
    }

    /**
     * 通过车名查询
     *
     * @param carName
     * @return
     */
    @GetMapping("findByCarName/{carName}")
    public JSONResult findByCarName(@PathVariable String carName) {
        List<Car> cars = carService.findByCarName(carName);
        return JSONResult.ok(cars);
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @GetMapping("deleteById/{id}")
    public JSONResult deleteById(@PathVariable int id) {
        carService.deleteById(id);
        return JSONResult.ok("车辆删除成功");
    }

    /**
     * 通过id更新全部信息
     *
     * @return
     */
    @PostMapping("updateById")
    public JSONResult updateById(Car car) {
        carService.updateById(car);
        return JSONResult.ok("车辆更新成功");
    }

    /**
     * 通过id增加
     *
     * @param car
     * @return
     */
    @PostMapping("insertCar")
    public JSONResult insertCar(Car car) {
        carService.insertCar(car);
        return JSONResult.ok("车辆添加成功");
    }

    /**
     * 购买车辆，购买前先锁定车辆
     *
     * @param carList 购车清单，json格式
     * @return
     */
    @PostMapping("buyCar")
    public synchronized JSONResult buyCar(@RequestBody List<Inventory> carList) {
        for (Inventory car:carList){
            Inventory inventory = carService.findCarInventory(car.getId());
            if (inventory.getCheckKey()!=null)
                return JSONResult.errorMsg("其他客户正在购买id为"+car.getId()+"的车，请稍后重试");
            if(inventory.getQuantity() < car.getQuantity())
                return JSONResult.errorMsg("id为"+car.getId()+"的车库存小于需求");

            car.setCheckKey(KeyUtil.getKey());
            carService.buyCar(car);
        }

        return JSONResult.ok("车辆购买成功");
    }

    /**
     * 查询车辆数据，以id排序，返回第from行到第to行之间的车辆
     * @param carName
     * @param from
     * @param to
     * @return
     */
    @GetMapping("findCarsByName/{carName}/{from}/{to}")
    public JSONResult findCarsByName(@PathVariable String carName, @PathVariable int from, @PathVariable
                                    int to) {
        int offset = from<1?1:from - 1;
        int rows = to - offset;
        List<Car> cars = carService.findCarsByName(carName, offset, rows);
        return JSONResult.ok(cars);
    }


    /**
     * 编辑车辆库存
     * @param inventory
     * @return
     */
    @PostMapping("editCarInventory")
    public JSONResult editCarInventory(Inventory inventory) {
        Inventory carInv=carService.findCarInventory(inventory.getId());
        if (carInv==null)
            carService.insertCarInventory(inventory);
        else
            carService.updateCarInventory(inventory);
        return JSONResult.ok("车辆库存更新成功");
    }
}
