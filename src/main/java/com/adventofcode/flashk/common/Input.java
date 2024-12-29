package com.adventofcode.flashk.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class Input {

	private static final String PATH_INPUTS = "src/main/resources";

	private Input() {
	}

	public static List<String> readStringLines(String inputFolder, String inputFile) {

		List<String> input;

		try {
			Path path = Paths.get(PATH_INPUTS, inputFolder, inputFile).toAbsolutePath();
			input = Files.lines(path).collect(Collectors.toList());

		} catch (IOException e) {
			input = new ArrayList<>();
			e.printStackTrace();
		}

		return input;
	}

}
