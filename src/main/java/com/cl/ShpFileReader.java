package com.cl;

import org.gdal.ogr.DataSource;
import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sganguly on 8/8/2017.
 */
public class ShpFileReader {
    private final Logger logger = LoggerFactory.getLogger(ShpFileReader.class);

    public List<Layer> readAllLayers(String shpfilesDir) {
        List<Layer> layerList = new ArrayList<>();

        // Open the data source
        DataSource ds = ogr.Open(shpfilesDir);

        if (ds == null) {
            logger.error("No Layer found !!!");
            return layerList;
        }
        // Get all layers
        int layerCnt = ds.GetLayerCount();
        for (int i = 0; i < layerCnt; i++) {
            layerList.add(ds.GetLayer(i));

        }
        return layerList;
    }
}
