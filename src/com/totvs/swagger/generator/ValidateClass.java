package com.totvs.swagger.generator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.totvs.swagger.generator.UtilClass;

import org.apache.commons.lang3.*;

public class ValidateClass {

	public boolean validaFonteProgress(String arquivoYAML, String pathCaminhoLeitura) throws IOException {

		// Verifica se existe a tag Program:
		if (!arquivoYAML.contains("program:")) {
			System.out.println("Não foi encontrada a chave de declaração do programa progress 'program:'.");
			return false;
		}

		// Verifica se existe a tag Program:
		if (!arquivoYAML.contains("procedure:")) {
			System.out.println("Não foi encontrada a declaração de procedures Progress");
			return false;
		}

		if (!this.contaProceduresMetodos(arquivoYAML)) {
			System.out.println(
					"O número de métodos estão diferente da quantidade de procedures decladaras no arqui YAML");
			return false;
		}

		String nomeProgramaProgress = null;

		// Encontra o nome do programa progress
		Scanner scanner = new Scanner(arquivoYAML);
		try {
			while (scanner.hasNextLine()) {
				if (scanner.next().contains("program:")) {
					nomeProgramaProgress = scanner.nextLine();
					break;
				}
			}

		} finally {
			scanner.close();
		}

		final String nomeProgrmaProgressFinal = nomeProgramaProgress.trim();

		File caminhoDoProgramasProgress = new File(pathCaminhoLeitura).getParentFile();

		File[] fonteProgress = caminhoDoProgramasProgress
				.listFiles((dir1, name) -> name.equals(nomeProgrmaProgressFinal));

		if (fonteProgress.length == 0) {
			System.out.println("Não foi encontrado o programa " + nomeProgramaProgress + ".");
			return false;
		}

		File programaProgress = fonteProgress[0];

		// Encontra o nome da procedure referenciada
		Scanner scannerProcedure = new Scanner(arquivoYAML);
		List<String> listaString = new ArrayList<String>();

		try {
			while (scannerProcedure.hasNextLine()) {
				if (scannerProcedure.hasNext()) {
					if (scannerProcedure.next().contains("procedure:")) {
						if (scannerProcedure.hasNextLine()) {
							listaString.add(scannerProcedure.nextLine());
						} else
							break;
					}
				} else
					break;
			}
		} finally {
			scannerProcedure.close();
		}

		String ProgramaProgressString = new UtilClass().readFileToString(programaProgress.toString());

		for (String procedure : listaString) {
			if (!ProgramaProgressString.contains("PROCEDURE " + procedure.trim() + ":")) {
				System.out.println(
						"O arquivo Yaml e o fonte progress, não estão equalizados conferir declarações de procedures.");
				return false;
			}
		}

		return true;
	}

	private boolean contaProceduresMetodos(String arquivoYaml) {
		int contadorMatches = 0;
		int contadorProcedures = 0;

		contadorProcedures = StringUtils.countMatches(arquivoYaml, "procedure:");

		contadorMatches += StringUtils.countMatches(arquivoYaml, "get:");
		contadorMatches += StringUtils.countMatches(arquivoYaml, "post:");
		contadorMatches += StringUtils.countMatches(arquivoYaml, "delete:");
		contadorMatches += StringUtils.countMatches(arquivoYaml, "put:");

		if (contadorProcedures == contadorMatches)
			return true;
		else
			return false;

	}

}
