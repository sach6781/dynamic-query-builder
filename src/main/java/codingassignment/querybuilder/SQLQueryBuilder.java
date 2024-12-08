package codingassignment.querybuilder;

import codingassignment.model.FilterCriteria;
import java.util.Map;

public class SQLQueryBuilder {

    public static String buildQuery(String tableName, FilterCriteria criteria) {
        StringBuilder query = new StringBuilder("SELECT * FROM ").append(tableName).append(" WHERE 1=1 ");

        // Filters
        for (Map.Entry<String, Object> filter : criteria.getFilters().entrySet()) {
            query.append(" AND ").append(filter.getKey()).append(" = ? ");
        }

        // Sorting
        if (criteria.getSortBy() != null) {
            query.append(" ORDER BY ").append(criteria.getSortBy());
            query.append(" ").append(criteria.getSortOrder() != null ? criteria.getSortOrder() : "ASC");
        }

        // Pagination
        int offset = (criteria.getPageNumber() - 1) * criteria.getPageSize();
        query.append(" LIMIT ").append(criteria.getPageSize()).append(" OFFSET ").append(offset);

        return query.toString();
    }
}
