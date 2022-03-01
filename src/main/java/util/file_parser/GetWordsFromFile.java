package util.file_parser;

import java.io.File;
import java.util.ArrayList;

public interface GetWordsFromFile {

    public ArrayList<WordAndCounter> getValidWords(File fileToRead);
    
	/*
	 * public ArrayList<WordAndCounter> getWordsInListWithPercentage(File
	 * fileToRead, int percentage);
	 */
}
