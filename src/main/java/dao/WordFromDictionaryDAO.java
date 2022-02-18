package dao;

import java.util.HashSet;


public interface WordFromDictionaryDAO {


	/*
	 * @Query("select c.word from WordFromDictionary c")
	 */    
    HashSet<String> getWordsFromDictionary();
	
}
