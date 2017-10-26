package com.cl;

import lombok.Cleanup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by sganguly on 8/8/2017.
 */
@Component
public class DBWriter {
    private final Logger logger = LoggerFactory.getLogger(DBWriter.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate batchTemplate;

    //@Value("${batch_size}")
    private int batchSize=10000;

    //@Value("${insert_sql}")
    private String insertSQL ="INSERT INTO structurefootprint (STRCT_ID, MD_ID, STATE_CODE,CNTY_CODE,AREA_SQ_FT, geom) VALUES (:strct_id, :md_id, :state_code, :cnty_code, :area_sq_ft,  ST_GeomFromText(:geometryInWKT, 4326))";

    public boolean writeToDB(List<SpatialDataRow> data) {
        List<List<SpatialDataRow>> batches = Lists.partition(data, batchSize);

        for (List<SpatialDataRow> itemBatch : batches) {
            SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(itemBatch.toArray());
            try {
                batchTemplate.batchUpdate(insertSQL, batch);
            } catch (Exception e) {
                logger.error("Exception while inserting data into table. Message: " + e.getMessage());
                return false;
            }
        }
        return true;
    }




}
