package com.totvs.swagger.generator;

import java.io.File;
import java.io.IOException;

public class GeneratorClass {

	public void mainGenerator(String[] args) throws IOException {

		System.out.println(
				"Automação de Geração Swagger, favor informar o caminho de leitura e gravação do arquivo de documentação.");

		String pathLeituraParam = "";
		String pathGravacaoParam = "";

		switch (args.length) {
		case 0:
			System.out.println("É preciso informar parâmetro para leitura e gravação do arquivo.");
			break;
		case 1:
			System.out.println("É preciso informar parâmetro para leitura e gravação do arquivo.");
			break;
		case 2:
			pathLeituraParam = args[0];
			pathGravacaoParam = args[1];
		default:
			break;
		}

		System.out.println("Path informado: " + pathLeituraParam);

		if (pathLeituraParam != null && !pathLeituraParam.isEmpty()) {

			File fileDirectories = new File(pathLeituraParam);

			if (!fileDirectories.isDirectory()) {
				System.out.println("O parâmetro informado não corresponde a um diretório.");
				return;
			}

			if (!fileDirectories.canRead()) {
				System.out.println("Você não tem permissão para ler o diretório informado.");
				return;
			}

			File[] listFilesAPIs = fileDirectories.listFiles((dir, name) -> {
				return name.toLowerCase().endsWith(".yaml");
			});

			if (listFilesAPIs != null) {

				ValidateClass valida = new ValidateClass();

				System.out.println("Iniciando leitura dos arquivos do caminho informado.");

				for (File file : listFilesAPIs) {

					String conteudoYaml = "";
					conteudoYaml = new UtilClass().readFileToString(file.getPath());

					System.out.println("Iniciando validação do arquivo informado.");

					if (valida.validaFonteProgress(conteudoYaml, pathLeituraParam)) {

						System.out.println("Convertendo o arquivo YAML para JSON");
						String conteudoJson = "";
						conteudoJson = UtilClass.convertToJson(conteudoYaml);

						System.out.println("Gerando Arquivo JSON");
						new UtilClass().gravaJsonGerado(conteudoJson, pathGravacaoParam, file.getName());

						System.out.println("Arquivo gerado com sucesso");

					} else
						System.out
								.println("Foram encontrados problemas na geração do arquivo, processo será abortado.");
				}

			} else
				System.out.println("Não foi encontrado nenhum arquivo .yaml para leitura.");

		}
	}

}
