package com.cl;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class SpatialDataRow {

    private double strct_id;
    private double md_id;
    private String state_code;
    private String cnty_code;
    private double area_sq_ft;
    private String geometryInWKT;

}
