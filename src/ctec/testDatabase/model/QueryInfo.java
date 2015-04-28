package ctec.testDatabase.model;

public class QueryInfo
{
	private String query;
	private long queryTime;
	
	/**
	 * Constructor for a QueryInfo
	 * @param query
	 * @param queryTime
	 */
	public QueryInfo(String query, long queryTime)
	{
		this.query = query;
		this.queryTime = queryTime;
	}
	
	/**
	 * Gets the query method.
	 * @return Returns the query method.
	 */
	public String getQuery()
	{
		return query;
	}
	
	/**
	 * Gets the queryTiem method.
	 * @return Returns the queryTime method.
	 */
	public long getQueryTime()
	{
		return queryTime;
	}
}
