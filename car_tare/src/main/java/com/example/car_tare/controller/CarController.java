package com.example.car_tare.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.car_tare.bean.LayuiResp;
import com.example.car_tare.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/findAll")
    public void findAll(HttpServletRequest request, HttpServletResponse response) {
        LayuiResp result = carService.findAll();
        PrintWriter pw = null;
        response.setCharacterEncoding("UTF-8");
        try {
            pw = response.getWriter();
            pw.write(JSONObject.toJSONString(result));
        }catch (Exception e) {
            log.error("e",e);
        }
    }
}

