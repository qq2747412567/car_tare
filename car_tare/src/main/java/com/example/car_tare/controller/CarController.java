package com.example.car_tare.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.car_tare.bean.LayuiResp;
import com.example.car_tare.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Slf4j
@Controller
public class CarController {
    @Resource
    private CarService carService;

    @RequestMapping("/oper")
    public String toOperCar() {
        return "index";
    }

    @RequestMapping("/search")
    public String toSearch() {
        return "search";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "add";
    }

    @RequestMapping("/findAll")
    public void findAll(HttpServletRequest request, HttpServletResponse response) {
        LayuiResp result = carService.findAll();
        PrintWriter pw = null;
        response.setCharacterEncoding("UTF-8");
        try {
            pw = response.getWriter();
            pw.write(JSONObject.toJSONString(result));
        } catch (Exception e) {
            log.error("e", e);
        }
    }

    @RequestMapping("/addCarTare")
    public void addCarTare(@RequestParam(value = "carId") String carId, @RequestParam(value = "weight") String weight, HttpServletResponse response) {
        LayuiResp result = carService.addCarTare(carId, weight);
        PrintWriter pw = null;
        response.setCharacterEncoding("UTF-8");
        try {
            pw = response.getWriter();
            pw.write(JSONObject.toJSONString(result));
        } catch (Exception e) {
            log.error("e", e);
        }
    }


    @RequestMapping("/searchForExcel")
    public void addCarTare(@RequestParam(value = "carId") String carId, @RequestParam(value = "mWeight") Double mWeight,
                           @RequestParam(value = "liao") String liao, HttpServletResponse response) {
        LayuiResp result = carService.searchForExcel(carId, mWeight, liao);
        PrintWriter pw = null;
        response.setCharacterEncoding("UTF-8");
        try {
            pw = response.getWriter();
            pw.write(JSONObject.toJSONString(result));
        } catch (Exception e) {
            log.error("e", e);
        }
    }

    @RequestMapping("/delCarTare")
    public void delCarTare(@RequestParam(value = "carId") String carId, HttpServletResponse response) {
        LayuiResp result = carService.delCarTare(carId);
        PrintWriter pw = null;
        response.setCharacterEncoding("UTF-8");
        try {
            pw = response.getWriter();
            pw.write(JSONObject.toJSONString(result));
        } catch (Exception e) {
            log.error("e", e);
        }
    }
}

