package com.example.car_tare.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
public class CarDao {
    @Resource
    private JdbcTemplate jdbcTemplate;
    public List<Map<String, Object>> findAll() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from car_tare");
        if(CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list;
    }
}
