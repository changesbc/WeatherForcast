package com.app.zhaobaocheng.weatherforcast.db;

import org.litepal.crud.DataSupport;

/**
 * Created by ZhaoBaocheng on 2017/3/21.
 */
public class Province extends DataSupport{
    private int id;
    private String provinceName;  //省的名字
    private int provinceCode;    //省的代号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
