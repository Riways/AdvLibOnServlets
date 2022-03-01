package dao;

import java.util.HashSet;

public interface OxfordThreeThousandDAO {

	public HashSet<String> getOxfThreeThousandWords();

	public void closeConnection();
}
