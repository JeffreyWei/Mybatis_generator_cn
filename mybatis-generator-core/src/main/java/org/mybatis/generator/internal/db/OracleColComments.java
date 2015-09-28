package org.mybatis.generator.internal.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Map;

/**
 * Created by wei on 15/9/28.
 */
public final class OracleColComments {
	public static Map<String, String> comments = new Hashtable<String, String>();

	/**
	 * 获取列注释
	 *
	 * @param tableName
	 * @param columnName
	 * @return
	 */
	public static String getComment(Connection con, String tableName, String columnName) {
		if (comments.size() == 0) {
			synchronized (OracleColComments.class) {
				if (comments.size() == 0) {
					synchronized (OracleColComments.class) {
						try {
							String sql = "select TABLE_NAME,COLUMN_NAME,COMMENTS from user_col_comments";
							Statement stmt = con.createStatement();
							ResultSet rs = stmt.executeQuery(sql);
							while (rs.next()) {
								comments.put(rs.getString("TABLE_NAME") + rs.getString("COLUMN_NAME"), rs.getString("COMMENTS"));
							}
							rs.close();
							stmt.close();
						} catch (Exception e) {
						}
					}
				}
			}
		}
		return comments.containsKey(tableName + columnName) ? comments.get(tableName + columnName) : "";
	}
}