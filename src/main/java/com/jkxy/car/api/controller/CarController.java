package com.jkxy.car.api.controller;

import com.jkxy.car.api.pojo.Car;
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
        return JSONResult.ok();
    }

    /**
     * 通过id更新全部信息
     *
     * @return
     */
    @PostMapping("updateById")
    public JSONResult updateById(Car car) {
        carService.updateById(car);
        return JSONResult.ok();
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
        return JSONResult.ok();
    }

    /**
     * 购买车辆，购买前先锁定车辆
     *
     * @param id
     * @return
     */
    @GetMapping("buyCar/{id}")
    public synchronized JSONResult buyCar(@PathVariable int id) {
        Car car = carService.findById(id);
        if (car.getCheckKey()!=null)
            return JSONResult.errorMsg("其他客户正在购买，请稍后重试");
        if(car.getQuantity()==0)
            return JSONResult.errorMsg("库存为0");

        String checkKey = KeyUtil.getKey();
        carService.lockCar(id, checkKey);
        carService.buyCar(id, checkKey);
        return JSONResult.ok();
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
}
