package com.example.car_tare.service;

import com.example.car_tare.bean.LayuiResp;
import com.example.car_tare.dao.CarDao;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
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

    public LayuiResp addCarTare(String carId, String weight) {
        //先根据车牌号删除之前的
        carDao.delCarTareById(carId);
        //新增
        carDao.addCarTare(carId,weight);
        LayuiResp layuiResp = new LayuiResp();
        layuiResp.setCode(0);
        layuiResp.setMsg("成功");
        return layuiResp;
    }

    public LayuiResp searchForExcel(String carId, Double mWeight, String liao) {
        LocalTime nowTime = LocalTime.now();
        LayuiResp layuiResp = new LayuiResp();
        //先根据车牌号 找到对应皮重 没有则提示
        try {
            Double tare = carDao.finCarTareById(carId);
            Double jWeight = mWeight - tare;
            //封装数据
            Map<String,Object> map = new HashMap<>();
            map.put("carId",carId);//车牌号
            map.put("tare",tare);//皮重
            map.put("mWeight",mWeight);//毛重
            map.put("jWeight",jWeight);//净重
            map.put("liao",liao);//料
            map.put("createTime",nowTime.getHour() + ":" + nowTime.getMinute() + ":" + nowTime.getSecond());
            //写入excel中
            writeIntoExcel(map);
            layuiResp.setCode(0);
            layuiResp.setMsg("成功");
            ArrayList<Map<String,Object>> objects = Lists.newArrayList();
            objects.add(map);
            layuiResp.setData(objects);
        }catch (Exception e) {
            layuiResp.setCode(-1);
            layuiResp.setMsg("该车牌号还未录入皮重，请录入后重试！");
        }
        return layuiResp;
    }

    private void writeIntoExcel(Map<String, Object> map) {
        LocalDate now = LocalDate.now();
        File file = new File("d:\\log\\车辆信息表_" + now + ".xlsx");
        if(!file.exists()) {
            //文件不存在，先生成并增加表头
            createNewExcel(now);
        }
        try {
            FileInputStream fs=new FileInputStream("d:\\log\\车辆信息表_" + now + ".xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(fs);
            XSSFSheet sheet1 = workbook.getSheet("Sheet1");
            int lastRow = sheet1.getLastRowNum();
            Row row0 = sheet1.createRow(lastRow + 1);
            Cell cell0 = row0.createCell(0);
            cell0.setCellValue((String) map.get("carId"));
            Cell cell1 = row0.createCell(1);
            cell1.setCellValue((Double) map.get("tare"));
            Cell cell2 = row0.createCell(2);
            cell2.setCellValue((Double) map.get("mWeight"));
            Cell cell3 = row0.createCell(3);
            cell3.setCellValue((Double) map.get("jWeight"));
            Cell cell4 = row0.createCell(4);
            cell4.setCellValue((String) map.get("liao"));
            Cell cell5 = row0.createCell(5);
            cell5.setCellValue((String) map.get("createTime"));
            OutputStream os = null;
            try {
                os = new FileOutputStream("d:\\log\\车辆信息表_"+now+".xlsx");
                os.flush();
                workbook.write(os);
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void createNewExcel(LocalDate now) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);
        XSSFSheet sheet = workbook.createSheet("Sheet1");
        Row row0 = sheet.createRow(0);

        Cell cell0 = row0.createCell(0);
        cell0.setCellValue("车牌号");
        cell0.setCellStyle(cellStyle);


        Cell cell1 = row0.createCell(1);
        cell1.setCellValue("皮重");
        cell0.setCellStyle(cellStyle);

        Cell cell2 = row0.createCell(2);
        cell2.setCellValue("毛重");
        cell0.setCellStyle(cellStyle);

        Cell cell3 = row0.createCell(3);
        cell3.setCellValue("净重");
        cell0.setCellStyle(cellStyle);

        Cell cell4 = row0.createCell(4);
        cell4.setCellValue("料");
        cell0.setCellStyle(cellStyle);

        Cell cell5 = row0.createCell(5);
        cell5.setCellValue("创建时间");
        cell0.setCellStyle(cellStyle);

        OutputStream os = null;
        try {
            os = new FileOutputStream("d:\\log\\车辆信息表_"+now+".xlsx");
            os.flush();
            workbook.write(os);
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LayuiResp delCarTare(String carId) {
        carDao.delCarTareById(carId);
        LayuiResp layuiResp = new LayuiResp();
        layuiResp.setCode(0);
        layuiResp.setMsg("成功");
        return layuiResp;
    }
}
