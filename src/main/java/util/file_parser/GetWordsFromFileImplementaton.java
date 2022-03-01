
package util.file_parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import dao.DictionaryDAO;
import dao.OxfordThreeThousandDAO;
import dao.implementation.DictionaryDAOImpl;
import dao.implementation.OxfordThreeThousandDAOImpl;

public class GetWordsFromFileImplementaton implements GetWordsFromFile {

	private static final Logger LOGGER = Logger.getLogger(GetWordsFromFileImplementaton.class);

	@Override
	public ArrayList<WordAndCounter> getValidWords(File fileToRead) {

		ArrayList<WordAndCounter> vaildWords = getWordsInList(fileToRead);

		removeInvalidWords(vaildWords);

		checkWordsInOxfordThreeThousand(vaildWords);

		return vaildWords;
	}

	private ArrayList<WordAndCounter> getWordsInList(File fileToRead) {
		// Get all words from file in ArrayList
		String str;
		ArrayList<WordAndCounter> wordsFromFile = new ArrayList<WordAndCounter>();
		Pattern pattern = Pattern.compile("[A-Za-z]+[']*[A-Za-z]+");
		HashSet<String> wordsWithoutCounter = new HashSet<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(fileToRead))) {
			while ((str = reader.readLine()) != null) {
				str = str.toLowerCase();
				Matcher matcher = pattern.matcher(str);
				while (matcher.find()) {
					String word = matcher.group();
					if (wordsWithoutCounter.contains(word)) {
						for (int i = 0; i < wordsFromFile.size(); i++) {
							if (wordsFromFile.get(i).getWord().equals(word)) {
								wordsFromFile.get(i).setCounter(wordsFromFile.get(i).getCounter() + 1);
								break;
							}
						}
					} else {
						wordsWithoutCounter.add(word);
						wordsFromFile.add(new WordAndCounter(word, 1));
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(wordsFromFile);

		LOGGER.debug(wordsFromFile.size() + " words readed!");

		return wordsFromFile;
	}

	/*
	 * @Override public ArrayList<WordAndCounter> getWordsInListWithPercentage(File
	 * fileToRead, int percentage) { if (percentage < 0 || percentage > 100) { throw
	 * new ArithmeticException("Percentage must be between 0 and 100"); }
	 * ArrayList<WordAndCounter> wordsWithPercentage = getWordsInList(fileToRead);
	 * wordsWithPercentage = getSpecificPercentOfWords(wordsWithPercentage,
	 * percentage); return wordsWithPercentage; }
	 */

	/*
	 * private ArrayList<WordAndCounter>
	 * getSpecificPercentOfWords(ArrayList<WordAndCounter> listToFilter, int
	 * percentage) {
	 * 
	 * int counter = 0;
	 * 
	 * //Counting summary amount of words for (int i = 0; i < listToFilter.size();
	 * i++) { counter += listToFilter.get(i).getCounter(); }
	 * 
	 * //calculating the number of words that make up the specified percentage int
	 * counterPercentOfWords = counter * percentage / 100;
	 * 
	 * 
	 * counter = 0; int lastIndex = 0; for (int i = 0; counter <
	 * counterPercentOfWords; i++) { counter += listToFilter.get(i).getCounter();
	 * if(counter >= counterPercentOfWords){ LOGGER.debug("Words summary: " +
	 * counter); lastIndex = i; } } LOGGER.debug("Percentage: " + percentage + "%");
	 * LOGGER.debug(lastIndex + " words out of " + listToFilter.size() +
	 * " constitute " + percentage + " % of the text"); ArrayList<WordAndCounter>
	 * result = new ArrayList<>(); for (int i = 0; i < lastIndex; i++) {
	 * result.add(listToFilter.get(i)); } return result; }
	 */

	/**
	 * Filtering out words not found in the dictionary
	 * 
	 * @param wordsToCheck a list of words to be checked
	 */
	private void removeInvalidWords(ArrayList<WordAndCounter> wordsToCheck) {
		DictionaryDAO dictWordsDAO = new DictionaryDAOImpl();
		HashSet<String> setOfWords = dictWordsDAO.getWordsFromDictionary();
		int originalSize = wordsToCheck.size();
		int finalSize;
		for (int i = 0; i < wordsToCheck.size(); i++) {
			if (!setOfWords.contains(wordsToCheck.get(i).getWord())) {
				/*
				 * LOGGER.debug("Invalid word: " + wordsToCheck.get(i) + " removed" );
				 */
				wordsToCheck.remove(i);

			}
		}
		finalSize = wordsToCheck.size();
		int percent = finalSize * 100 / originalSize;
		LOGGER.debug(percent + " % of words was found on dictionary");
		LOGGER.debug("Words checked in dictionary! " + wordsToCheck.size() + " words left in list");
	}

	private void checkWordsInOxfordThreeThousand(ArrayList<WordAndCounter> wordsToCheck) {
		OxfordThreeThousandDAO oxfDAO = new OxfordThreeThousandDAOImpl();
		HashSet<String> setOfWords = oxfDAO.getOxfThreeThousandWords();
		int counter = 0;
		int percent;
		for (int i = 0; i < wordsToCheck.size(); i++) {
			WordAndCounter wordToCheck = wordsToCheck.get(i);
			if (setOfWords.contains(wordToCheck.getWord())) {
				wordToCheck.setWordInOxford3000(true);
				counter++;
			}
		}
		percent = (counter * 100) / wordsToCheck.size();
		LOGGER.debug(percent + " % of words was found in oxford3000 list");

	}

}
