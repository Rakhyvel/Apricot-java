package com.josephs_projects.apricotLibrary;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Lexicon {
	List<String> records;
	HashMap<String, Double> morphemes;
	List<String> endings;
	int portionSize;

	public Lexicon(String path, int portionSize, int endPortionSize) {
		if (portionSize > endPortionSize) {
			System.err.println("EndPortionSize must be greater than or equal to regular portion size");
		}
		records = readFile(path, portionSize);
		morphemes = getHashes(records, portionSize);
		endings = getEndings(records, endPortionSize);
		this.portionSize = portionSize;
	}

	public String randName(int lowerEnd, int upperEnd) {
		String portion;
		String name;
		boolean foundFlag = false;
		do {
			portion = records.get(Apricot.rand.nextInt(records.size())).substring(0, portionSize);
			name = portion;
			for (int i = portionSize; i < upperEnd; i++) {
				String randPortion = spitOutPossibilities(morphemes, portion, portionSize);
				if (randPortion == null) {
					// If there are no matches
					portion = records.get(Apricot.rand.nextInt(records.size())).substring(0, portionSize - 1);
					name = portion;
					i--;
					continue;
				}
				name += randPortion.substring(portionSize - 1, portionSize);
				Collections.shuffle(endings);
				if (name.length() >= lowerEnd) {
					for (String ending : endings) {
						if (name.substring(name.length() - portionSize).equals(ending.substring(0, portionSize))) {
							name += ending.substring(portionSize);
							foundFlag = true;
							return name;
						}
					}
				}
				portion = randPortion.substring(1);
				if (name.length() > upperEnd) {
					name = "+";
				}
			}
		} while (!foundFlag);
		return name; // Capitalize string
	}

	private static List<String> readFile(String path, int portionSize) {
		List<String> records = new ArrayList<String>();

		InputStream in = Lexicon.class.getResourceAsStream(path);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.length() > portionSize)
					records.add(line);
			}
		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", path);
			e.printStackTrace();
			return null;
		}
		return records;
	}

	private static HashMap<String, Double> getHashes(List<String> records, int portionSize) {
		// Converting the strings into seperated values
		HashMap<String, Double> probabilities = new HashMap<String, Double>();
		for (int i = 0; i < records.size(); i++) {
			for (int i2 = 0; i2 < records.get(i).length() - portionSize + 1; i2++) {
				String sub = records.get(i).substring(i2, i2 + portionSize);
				if (probabilities.containsKey(sub)) {
					probabilities.replace(sub, probabilities.get(sub) + 1);
				} else {
					probabilities.put(sub, 1.0);
				}
			}
		}
		return probabilities;
	}

	private static List<String> getEndings(List<String> records, int portionSize) {
		// Converting the strings into seperated values
		List<String> probabilities = new ArrayList<>();
		for (String record : records) {
			if (record.length() < portionSize)
				continue;

			String sub = record.substring(record.length() - portionSize);
			probabilities.add(sub);
		}
		return probabilities;
	}

	private static String spitOutPossibilities(HashMap<String, Double> probabilities, String target, int portionSize) {
		ArrayList<String> lettersList = new ArrayList<String>();
		ArrayList<Double> lettersProbability = new ArrayList<Double>();
		for (String key : probabilities.keySet()) {
			if (key.substring(0, portionSize - 1).equals(target)) {
				lettersList.add(key);
				lettersProbability.add(probabilities.get(key));
			}
		}
		return getProbability(lettersList, lettersProbability);
	}

	private static String getProbability(ArrayList<String> lettersList, ArrayList<Double> lettersProbability) {
		// 1/rank = proabability
		// Imagine chosing a random angle on a pie chart with different
		// segmentsItem[] items = ...;

		// Compute the total weight of all items together
		if (lettersProbability.isEmpty())
			return null;
		double totalWeight = 0;
		for (int i = 0; i < lettersProbability.size(); i++) {
			totalWeight += lettersProbability.get(i);
		}
		// Now choose a random item
		int randomIndex = -1;
		double random = (Math.random()) * totalWeight;
		for (int i = 0; i < lettersList.size(); ++i) {
			random -= lettersProbability.get(i);
			if (random <= 0.0d) {
				randomIndex = i;
				break;
			}
		}
		return lettersList.get(Math.max(0, randomIndex));
	}
}
