package dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import org.apache.log4j.Logger;

import constants.Query;
import dao.ConnectionPool;
import dao.DictionaryDAO;

public class DictionaryDAOImpl implements DictionaryDAO {

	private final static Logger LOGGER = Logger.getLogger(DictionaryDAOImpl.class);

	private Connection connection;

	private static HashSet<String> allWords;

	@Override
	public HashSet<String> getWordsFromDictionary() {
		if(allWords == null) {
			initWordsData();
		}
		return allWords;
	}
	
	
	private void initWordsData() {
		connection = ConnectionPool.getConnection();

		allWords = new HashSet<String>();

		ResultSet resSet = null;

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.SELECT_ALL_WORDS_FROM_DICTIONARY)) {

			resSet = prepSttmnt.executeQuery();

			while (resSet.next()) {
				allWords.add(resSet.getString(1));
			}
			LOGGER.debug(" all words obtained from DB: " + allWords);
		} catch (SQLException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
		} finally {
			try {
				resSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closeConnection();
		}
	}

	@Override
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		}
	}

}
