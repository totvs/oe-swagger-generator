package com.totvs.swagger.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;

public class UtilClass {

	public String readFileToString(String pathname) throws IOException {

		File file = new File(pathname);
		StringBuilder fileContents = new StringBuilder((int) file.length());
		Scanner scanner = new Scanner(file, "UTF-8");
		String lineSeparator = System.getProperty("line.separator");

		try {

			while (scanner.hasNextLine()) {
				fileContents.append(scanner.nextLine() + lineSeparator);
			}

			return fileContents.toString();

		} finally {
			scanner.close();
		}
	}

	public void gravaJsonGerado(String conteudoJson, String pathGravacaoParam, String nomeArquivo) throws IOException {
		try {

			String text = conteudoJson;
			Path path = Paths.get(pathGravacaoParam, nomeArquivo + ".json");

			File arquivoJson = path.toFile();
			arquivoJson.createNewFile();

			Files.write(path, Arrays.asList(text));

		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public static String convertToJson(String yamlString) {
		Yaml yaml = new Yaml();

		Map<String, Object> map = (Map<String, Object>) yaml.load(yamlString);

		JSONObject jsonObject = new JSONObject(map);
		return jsonObject.toString();
	}
}
