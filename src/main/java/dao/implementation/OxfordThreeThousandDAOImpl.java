package dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import org.apache.log4j.Logger;

import constants.Query;
import dao.ConnectionPool;
import dao.OxfordThreeThousandDAO;

public class OxfordThreeThousandDAOImpl implements OxfordThreeThousandDAO {

	private final static Logger LOGGER = Logger.getLogger(OxfordThreeThousandDAOImpl.class);

	private Connection connection;

	private static HashSet<String> allWords;

	@Override
	public HashSet<String> getOxfThreeThousandWords() {
		if (allWords == null) {
			initWordsData();
		}
		return allWords;
	}

	private void initWordsData() {
		connection = ConnectionPool.getConnection();

		allWords = new HashSet<String>();

		ResultSet resSet = null;

		try (PreparedStatement prepSttmnt = connection.prepareStatement(Query.SELECT_ALL_WORDS_FROM_OXFORD_THREE_THOUSAND)) {

			resSet = prepSttmnt.executeQuery();

			while (resSet.next()) {
				allWords.add(resSet.getString(1));
			}
			LOGGER.debug("words obtained from oxford3000: " + allWords.size());
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
