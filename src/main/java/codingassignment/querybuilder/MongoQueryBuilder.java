package codingassignment.querybuilder;

import codingassignment.model.FilterCriteria;
import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import java.util.Map;

public class MongoQueryBuilder {

    public static Bson buildFilter(FilterCriteria criteria) {
        Bson filter = Filters.empty();
        for (Map.Entry<String, Object> entry : criteria.getFilters().entrySet()) {
            filter = Filters.and(filter, Filters.eq(entry.getKey(), entry.getValue()));
        }
        return filter;
    }

    public static Bson buildSort(FilterCriteria criteria) {
        if (criteria.getSortBy() != null) {
            return criteria.getSortOrder().equalsIgnoreCase("DESC") ?
                   Sorts.descending(criteria.getSortBy()) :
                   Sorts.ascending(criteria.getSortBy());
        }
        return null;
    }
}
