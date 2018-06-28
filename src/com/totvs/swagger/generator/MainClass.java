package com.totvs.swagger.generator;

import java.io.IOException;

import com.totvs.swagger.generator.GeneratorClass;

public class MainClass {

	public static void main(String[] args) throws IOException {
		GeneratorClass main = new GeneratorClass();
		main.mainGenerator(args);
	}

}
