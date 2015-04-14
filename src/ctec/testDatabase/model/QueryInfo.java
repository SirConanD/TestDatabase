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
	
	public String getQuery()
	{
		return query;
	}
	
	public long getQueryTime()
	{
		return queryTime;
	}
}
