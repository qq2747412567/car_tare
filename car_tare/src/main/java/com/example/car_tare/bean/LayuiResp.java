package com.example.car_tare.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class LayuiResp implements Serializable {
    private Integer code;
    private String msg;
    private Integer count;
    private List<Map<String,Object>> data;
}
