package dao;

import java.util.HashSet;

public interface DictionaryDAO {

	public HashSet<String> getWordsFromDictionary();

	public void closeConnection();

}
