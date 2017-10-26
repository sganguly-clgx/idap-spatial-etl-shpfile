package com.cl;

import org.gdal.ogr.Feature;
import org.gdal.ogr.FieldDefn;
import org.gdal.ogr.Geometry;
import org.gdal.ogr.Layer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by sganguly on 8/8/2017.
 */
public class ShpFileLayerReader {
    private final Logger logger = LoggerFactory.getLogger(ShpFileLayerReader.class);

    public List<SpatialDataRow> readLayer(Layer layer) {
        List<SpatialDataRow> rows = new ArrayList<>();

        if (layer == null) {
            return rows;
        }

        int featureCnt = layer.GetFeatureCount();

        logger.debug("Number of Features = " + featureCnt);

        // Convert all features to JSON
        for (int i = 1; i <= featureCnt; i++) {
            Feature f = layer.GetNextFeature();
            if (f == null) {
                logger.debug("No more features found.");
                break;
            }
            LinkedHashMap<String, String> content = new LinkedHashMap<String, String>();

            Geometry geom = f.GetGeometryRef();
            String wkt = geom.ExportToWkt();
            int fldCnt = f.GetFieldCount();
            SpatialDataRow row = new SpatialDataRow();

            for (int j = 0; j < fldCnt; j++) {
                FieldDefn fldDef = f.GetFieldDefnRef(j);
                content.put(fldDef.GetName(), f.GetFieldAsString(j));
            }
            row.setStrct_id(Double.parseDouble(content.get("STRCT_ID")));
            row.setMd_id(Double.parseDouble(content.get("MD_ID")));
            row.setState_code(content.get("STATE_CODE"));
            row.setCnty_code(content.get("CNTY_CODE"));
            row.setArea_sq_ft(Double.parseDouble(content.get("AREA_SQ_FT")));
            row.setGeometryInWKT(wkt);
            rows.add(row);

        }

        return rows;
    }
}
