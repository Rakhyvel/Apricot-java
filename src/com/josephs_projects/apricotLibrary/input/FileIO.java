package com.josephs_projects.apricotLibrary.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileIO {
	public static List<String> readTextFile(String path) throws IOException {
		List<String> retval = new ArrayList<>();
		File file = new File(path);
		String line;
		BufferedReader in = new BufferedReader(new FileReader(file));
		while ((line = in.readLine()) != null) {
			retval.add(line);
		}
		in.close();
		return retval;
	}
}
