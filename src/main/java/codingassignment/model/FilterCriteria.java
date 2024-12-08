package codingassignment.model;

import java.util.Map;

public class FilterCriteria {
    private Map<String, Object> filters; // key-value filters (e.g., name="John", city="NYC")
    private String sortBy;
    private String sortOrder; // ASC or DESC
    private int pageNumber;
    private int pageSize;

    public FilterCriteria(Map<String, Object> filters, String sortBy, String sortOrder, int pageNumber, int pageSize) {
        this.filters = filters;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    // Getters
    public Map<String, Object> getFilters() { return filters; }
    public String getSortBy() { return sortBy; }
    public String getSortOrder() { return sortOrder; }
    public int getPageNumber() { return pageNumber; }
    public int getPageSize() { return pageSize; }
}
