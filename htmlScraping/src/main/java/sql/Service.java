/*package sql;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Service {
	
	public static JdbcTemplate jdbcTemplate = JdbcTemplateFactory.getJdbcTemplate();

	public static SqlRowSet queryForRowSet(String sql, Object[] args) {
		if (args == null) {
			return jdbcTemplate.queryForRowSet(sql);						
		} else{
			return jdbcTemplate.queryForRowSet(sql, args);
		}		
	}
	
	public static String querySelect(String sql, Object[] args) {
		if (args == null) {
			return jdbcTemplate.queryForObject(sql, String.class);						
		} else{
			return jdbcTemplate.queryForObject(sql, String.class, args);
		}
	}
	
	public static int existCheck(String sql, Object[] args) {
		return jdbcTemplate.queryForInt(sql, args);
	}

	public static void queryUpdate(String sql, Object[] args) {
		if (args == null) {
			jdbcTemplate.update(sql);					
		} else{
			jdbcTemplate.update(sql, args);
		}
	}
	
}
*/