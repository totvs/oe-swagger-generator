package com.totvs.swagger.generator;

import java.io.File;
import java.io.IOException;

public class GeneratorClass {

	public void mainGenerator(String readPath, String writePath) throws IOException {

		System.out.println("Automacao de Geracao Swagger");

		String pathLeituraParam = readPath;
		String pathGravacaoParam = writePath;

		System.out.println("Path informado: " + pathLeituraParam);

		if (pathLeituraParam != null && !pathLeituraParam.isEmpty()) {

			File fileDirectories = new File(pathLeituraParam);

			if (!fileDirectories.isDirectory()) {
				throw new IOException("O parametro informado nao corresponde a um diretorio.");
			}

			if (!fileDirectories.canRead()) {
				throw new IOException("Voce nao tem permissao para ler o diretorio informado.");
			}

			File[] listFilesAPIs = fileDirectories.listFiles((dir, name) -> {
				return name.toLowerCase().endsWith(".yaml");
			});

			if (listFilesAPIs.length != 0) {
				ValidateClass valida = new ValidateClass();

				System.out.println("Iniciando leitura dos arquivos");

				for (File file : listFilesAPIs) {

					String conteudoYaml = "";
					conteudoYaml = new UtilClass().readFileToString(file.getPath());

					System.out.println("Iniciando validacao do arquivo " + file.getName());

					if (valida.validaFonteProgress(conteudoYaml, pathLeituraParam)) {

						System.out.println("Convertendo o arquivo YAML para JSON");
						String conteudoJson = "";
						conteudoJson = new UtilClass().convertToJson(conteudoYaml);

						System.out.println("Gerando Arquivo JSON");
						new UtilClass().gravaJsonGerado(conteudoJson, pathGravacaoParam, file.getName());

						System.out.println("Arquivo gerado com sucesso");
					} else
						throw new IOException(
								"Foram encontrados problemas na geracao do arquivo, processo sera abortado.");
				}
			} else
				System.out.println("Nao foi encontrado nenhum arquivo .yaml para leitura.");
		}
	}
}
