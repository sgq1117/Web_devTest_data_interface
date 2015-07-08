package xuanniuSgq;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.mysql.jdbc.Connection;
import com.xuanniu.util.JsonData;

/**
 * @author sungq
 * @version 创建时间：2015年5月15日 下午4:56:09 类说明
 */

public class Xuanniu_db {
	/**
	 * 连接数据库
	 * 
	 * @param db
	 * @return
	 */
	public static Connection getConn(String db) {
		Connection conn = null;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://172.32.21.6:3306/" + db + "?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&rewriteBatchedStatements=true&useAffectedRows=true";
		String userName = "root";
		String password = "mysqlniutest";

		try {
			Class.forName(driver); // classLoader,加载对应驱动
			conn = (Connection) DriverManager.getConnection(url, userName, password);
		} catch (ClassNotFoundException e) {
			System.out.println("classNotFound exception");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("sql exception");
			e.printStackTrace();
		}

		return conn;
	}
	/**
	 * 插入操作
	 * @param db
	 * @param sql
	 * @return
	 */
	public int insert(String db, String sql) {
		Connection conn = getConn(db);
		int i = 0;
		PreparedStatement ps;

		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			i = ps.executeUpdate();
			System.out.println("insert: " + i);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQL insert error.....");
			e.printStackTrace();
		}
		return i;

	}

	/**
	 * 删除操作
	 * 
	 * @param db
	 * @param sql
	 * @return
	 */
	public int delRow(String db, String sql) {
		// System.out.print("del sql: "+sql);
		Connection conn = getConn(db);
		int i = 0;
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			i = ps.executeUpdate();

			// System.out.println("delete: " + i);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQL del error.....");
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * update 更新表数据；
	 * 
	 * @param db
	 * @param sql
	 * @return
	 */
	public int updateColumn(String db, String sql) {
		Connection conn = getConn(db);
		int i = 0;
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			i = ps.executeUpdate();
			// System.out.println("update: " + i);
			ps.close();
			conn.close();
		} catch (SQLException e) {
			System.out.println("SQL update error.....");
			e.printStackTrace();
		}
		return i;

	}

	/**
	 * Search查询--将查询结果集组成json对象
	 * 
	 * @param sql
	 */
	public JSONObject searchColumn(String db, String sql) {
		Map<String, String> map = new HashMap<String, String>();
		JSONObject json = null;
		Connection conn = getConn(db);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = (PreparedStatement) conn.prepareStatement(sql);
			rs = ps.executeQuery();
			java.sql.ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			while (rs.next()) {
				// 遍历每一列
				for (int i = 1; i <= columnCount; i++) {
					String columnName = metaData.getColumnLabel(i);
					String value = rs.getString(columnName);
					map.put(columnName, value);
				}
			}
			json = JSONObject.fromObject(map);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();// 关闭记录集
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			if (ps != null) {
				try {
					ps.close();// 关闭声明
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();// 关闭连接
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return json;
	}

	public static void main(String[] args) {
		Xuanniu_db xd0 = new Xuanniu_db();
		
		
		String sql = "update xuanniu_security.security_user_protect set id_number = '320804197604099431',name = '洪大华',id_number_status ='1',id_photo_status='3' where uid in('567')";
		xd0.updateColumn("xuanniu_trade", sql);

	}
}
