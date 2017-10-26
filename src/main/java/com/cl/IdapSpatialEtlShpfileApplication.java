package com.cl;

import org.gdal.ogr.Layer;
import org.gdal.ogr.ogr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class IdapSpatialEtlShpfileApplication implements CommandLineRunner {

	@Autowired
	DBWriter writer;

	public static void main(String[] args){

		SpringApplication app = new SpringApplication (IdapSpatialEtlShpfileApplication.class);
		ogr.RegisterAll();
		app.run(args);
	}

	@Override
	public void run(String... strings) throws Exception {
		ShpFileReader shpFileReader = new ShpFileReader();
		List<Layer> layers  = shpFileReader.readAllLayers("C:\\RM_SourceCode\\Rm\\boundary\\structurefootprint\\01");
		System.out.println("# of Layers = " + layers.size());
		for (Layer layer: layers){
			System.out.println("Processing Layer : " + layer.GetName());
			ShpFileLayerReader layerReader = new ShpFileLayerReader();
			List<SpatialDataRow> rows = layerReader.readLayer(layer);
			System.out.println("Number of Rows : " + rows.size());
			writer.writeToDB(rows);
		}
	}
}
