package com.jmu.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtil {
	/*
	 * 1.加载驱动
	 * 2.连接
	 * 3.获取连接对象
	 * 连接数据库四要素:
	 * 	1.用户名
	 * 	2.密码
	 * 	3.驱动
	 * 	4.URL
	 * 查询操作:
	 * 	1.连接数据库:Connection
	 * 	2.执行SQL语句:PreparedStatement
	 * 	3.获取结果集:ResultSet
	 */
	private static final String USERNAME="root";
	private static final String PASSWORD="19960515";
	//mysql驱动
	private static final String MYSQLDRIVER = "com.mysql.jdbc.Driver";
	//mysql连接地址URL setunicode表示使用Unicade字符集,characterEncoding表示使用utf-8(gbk)
	//jdbc:mysql://localhost:3306/stulq?setUnicode=true&characterEncoding=UTF-8;
	//mysqlURL
	private static final String MYSQLURL="jdbc:mysql://localhost:3306/test";
	//oracle驱动
	private static final String ORACLEDRIVER = "oracle.jdbc.driver.OracleDriver";
	//oracle URL
	private static final String ORACLEURL="jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	
	private static  Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
	//用于连接数据库
	public static Connection getConnection(){
		try {
			//加载驱动
			Class.forName(MYSQLDRIVER);
			//创建连接
			conn =  DriverManager.getConnection(MYSQLURL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//关闭对象
	public static void close(){
		//isClosed()判断连接是否关闭
		try {
			//关闭结果集对象
			if(rs!=null && !rs.isClosed()){
				rs.close();
			}
			//关闭执行SQL语句对象
			if(ps!=null && !ps.isClosed()){
				ps.close();
				
			}
			//关闭连接对象
			if(conn!=null && !conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//ps.executeQuery(); //查询
	public static ResultSet executeQuery(String sql, Object...objs){
		//创建数据库连接
		getConnection();
		try {
			ps = conn.prepareStatement(sql);
			for (int k = 0; k < objs.length; k++) {
				ps.setObject(k+1, objs[k]);
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	//ps.executeUpdate(); //添加,修改,删除
	//用于执行增删改的操作
	//通过动态传递参数的方式来解决参数不确定的情况
	public static int executeUpdate(String sql, Object ...obj){
		int cnt = 0;
		//创建数据库连接
		getConnection();
		try {
			ps = conn.prepareStatement(sql);
			//ps.setString(parameterIndex, x);
			//ps.setObject(1, objs);
			for (int k = 0; k < obj.length; k++) {
				 ps.setObject(k+1, obj[k]);
			}
			cnt = ps.executeUpdate();
			System.out.println("执行结果:"+cnt);
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cnt;
	}
}
