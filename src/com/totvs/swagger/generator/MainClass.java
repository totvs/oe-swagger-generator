package com.totvs.swagger.generator;

import java.io.IOException;

public class MainClass {

	private static final String PATH_PARAM = "-p";

	public static void main(String[] args) throws IOException {		
		GeneratorClass main = new GeneratorClass();
		main.mainGenerator(System.getProperty("readPath"), System.getProperty("writePath"));
	}
}
