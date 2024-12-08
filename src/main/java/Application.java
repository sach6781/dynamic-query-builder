import codingassignment.model.FilterCriteria;
import codingassignment.service.QueryService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

public class Application {
    public static void main(String[] args) {
        try {
            // Define Filters
            var filters = new HashMap<String, Object>();
            filters.put("name", "John Doe");
            filters.put("city", "New York");

            // Create FilterCriteria
            FilterCriteria criteria = new FilterCriteria(filters, "age", "ASC", 1, 10);

            // MySQL Query Execution
            System.out.println("Fetching results from MySQL...");
            List<Map<String, Object>> sqlResults = QueryService.executeSQLQuery("users", criteria);
            sqlResults.forEach(row -> System.out.println(row));

            // MongoDB Query Execution
            System.out.println("\nFetching results from MongoDB...");
            List<Document> mongoResults = QueryService.executeMongoQuery("users", criteria);
            mongoResults.forEach(doc -> System.out.println(doc.toJson()));

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}
