package com.example.car_tare.service;

import com.example.car_tare.bean.LayuiResp;
import com.example.car_tare.dao.CarDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class CarService {

    @Resource
    CarDao carDao;

    public LayuiResp findAll() {
        List<Map<String,Object>> list = carDao.findAll();
        LayuiResp layuiResp = new LayuiResp();
        layuiResp.setCode(0);
        layuiResp.setCount(list.size());
        layuiResp.setMsg("成功");
        layuiResp.setData(list);
        return layuiResp;
    }
}
