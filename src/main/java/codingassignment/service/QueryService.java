package codingassignment.service;

import codingassignment.config.DatabaseConfig;
import codingassignment.model.FilterCriteria;
import codingassignment.querybuilder.SQLQueryBuilder;
import codingassignment.querybuilder.MongoQueryBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class QueryService {

	private static final Logger logger = LoggerFactory.getLogger(QueryService.class);

	// Execute SQL Query
	public static List<Map<String, Object>> executeSQLQuery(String tableName, FilterCriteria criteria)
			throws Exception {
		List<Map<String, Object>> results = new ArrayList<>();
		String query = SQLQueryBuilder.buildQuery(tableName, criteria);
		logger.info("Executing SQL Query: {}", query);

		try (Connection conn = DatabaseConfig.getMySQLConnection();
				PreparedStatement ps = conn.prepareStatement(query)) {

			int index = 1;
			for (Object value : criteria.getFilters().values()) {
				ps.setObject(index++, value);
			}

			try (ResultSet rs = ps.executeQuery()) {
				int columnCount = rs.getMetaData().getColumnCount();

				while (rs.next()) {
					Map<String, Object> row = new HashMap<>();
					for (int i = 1; i <= columnCount; i++) {
						String columnName = rs.getMetaData().getColumnName(i);
						Object value = rs.getObject(i);
						row.put(columnName, value);
					}
					results.add(row);
				}
			}
			logger.info("SQL Query executed successfully, {} rows fetched.", results.size());
		} catch (Exception e) {
			logger.error("Error executing SQL Query: {}", e.getMessage());
			throw e;
		}
		return results;
	}

	// Execute MongoDB Query
	public static List<Document> executeMongoQuery(String collectionName, FilterCriteria criteria) {
		List<Document> results = new ArrayList<>();
		logger.info("Executing MongoDB Query on collection: {}", collectionName);

		try {
			MongoDatabase db = DatabaseConfig.getMongoConnection();
			MongoCollection<Document> collection = db.getCollection(collectionName);

			var filter = MongoQueryBuilder.buildFilter(criteria);
			var sort = MongoQueryBuilder.buildSort(criteria);

			collection.find(filter).sort(sort).skip((criteria.getPageNumber() - 1) * criteria.getPageSize())
					.limit(criteria.getPageSize()).into(results);

			logger.info("MongoDB Query executed successfully.");
		} catch (Exception e) {
			logger.error("Error executing MongoDB Query: {}", e.getMessage());
			throw e;
		}
		return results;
	}
}
