/*package sql;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplateFactory {
	
	private static JdbcTemplate jdbcTemplate;
	
	static {
		com.sun.security.auth.module.NTSystem NTSystem = new com.sun.security.auth.module.NTSystem();
		String dbpath;
		if(NTSystem.getName().equals("utjbd01")) {
			dbpath = "C:/Documents and Settings/" + NTSystem.getName() + "/workspace/BitZillion/src/BitZillion.db3";
		} else {
			dbpath = "C:/Users/" + NTSystem.getName() + "/workspace/BitZillion/src/BitZillion.db3";
		}
		
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("org.sqlite.JDBC");
		ds.setUrl("jdbc:sqlite:" + dbpath);
		ds.setInitialSize(3);
		ds.setMaxActive(10);
		ds.setMinIdle(10);
		ds.setPoolPreparedStatements(true);
	    jdbcTemplate = new JdbcTemplate(ds);
	}
	
	public static JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
*/