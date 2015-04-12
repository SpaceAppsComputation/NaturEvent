package com.example.asabater.pruebasmaps;

public class Earth {

	public static String getMiniatura(double latitud, double longitud, int zoom, String time) {
		int width = (int) Math.pow(2, zoom);
		int height = (int) Math.pow(2,zoom)/2;
		if (longitud > 140) {
			longitud = longitud - 140;
		} else {
			longitud = longitud + 180 + 40;
		}
		int col = (int) ((longitud * width)/ 360.0);

		if (latitud > 75) {
			latitud = latitud - 75;
		} else {
			latitud = latitud + 90 + 15;
		}
		int row = (int) ((latitud*height) / 180);
		// double longi = longitud + 230;
		// double latit = latitud + 90;
		// System.out.println(latit);
		// int width = (int) Math.pow(2, zoom);
		// System.out.println("Width: "+width);
		// double widthTile = 360.0 / (double) width;
		// double heightTile = 180.0 / (double) width;
		// System.out.println("WidthTile "+widthTile);
		// int row = (int) (latit / heightTile);
		// int col = (int) (longi / widthTile);
		String peticion = "http://map1.vis.earthdata.nasa.gov/wmts-geo/wmts.cgi"
				+ "?SERVICE=WMTS"
				+ "&REQUEST=GetTile"
				+ "&VERSION=1.0.0"
				+ "&LAYER=MODIS_Terra_CorrectedReflectance_TrueColor"
				+ "&STYLE=&TILEMATRIXSET=EPSG4326_250m"
				+ "&TILEMATRIX="
				+ zoom
				+ "&TILEROW="
				+ row
				+ "&TILECOL="
				+ col
				+ "&FORMAT=image%2Fjpeg" + "&TIME=" + time;
		return peticion;
	}
}
