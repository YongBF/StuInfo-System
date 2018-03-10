package com.jmu.unit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtil {
	/*
	 * 1.��������
	 * 2.����
	 * 3.��ȡ���Ӷ���
	 * �������ݿ���Ҫ��:
	 * 	1.�û���
	 * 	2.����
	 * 	3.����
	 * 	4.URL
	 * ��ѯ����:
	 * 	1.�������ݿ�:Connection
	 * 	2.ִ��SQL���:PreparedStatement
	 * 	3.��ȡ�����:ResultSet
	 */
	private static final String USERNAME="root";
	private static final String PASSWORD="19960515";
	//mysql����
	private static final String MYSQLDRIVER = "com.mysql.jdbc.Driver";
	//mysql���ӵ�ַURL setunicode��ʾʹ��Unicade�ַ���,characterEncoding��ʾʹ��utf-8(gbk)
	//jdbc:mysql://localhost:3306/stulq?setUnicode=true&characterEncoding=UTF-8;
	//mysqlURL
	private static final String MYSQLURL="jdbc:mysql://localhost:3306/test";
	//oracle����
	private static final String ORACLEDRIVER = "oracle.jdbc.driver.OracleDriver";
	//oracle URL
	private static final String ORACLEURL="jdbc:oracle:thin:@127.0.0.1:1521:orcl";
	
	private static  Connection conn;
	private static PreparedStatement ps;
	private static ResultSet rs;
	//�����������ݿ�
	public static Connection getConnection(){
		try {
			//��������
			Class.forName(MYSQLDRIVER);
			//��������
			conn =  DriverManager.getConnection(MYSQLURL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	//�رն���
	public static void close(){
		//isClosed()�ж������Ƿ�ر�
		try {
			//�رս��������
			if(rs!=null && !rs.isClosed()){
				rs.close();
			}
			//�ر�ִ��SQL������
			if(ps!=null && !ps.isClosed()){
				ps.close();
				
			}
			//�ر����Ӷ���
			if(conn!=null && !conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//ps.executeQuery(); //��ѯ
	public static ResultSet executeQuery(String sql, Object...objs){
		//�������ݿ�����
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
	
	//ps.executeUpdate(); //���,�޸�,ɾ��
	//����ִ����ɾ�ĵĲ���
	//ͨ����̬���ݲ����ķ�ʽ�����������ȷ�������
	public static int executeUpdate(String sql, Object ...obj){
		int cnt = 0;
		//�������ݿ�����
		getConnection();
		try {
			ps = conn.prepareStatement(sql);
			//ps.setString(parameterIndex, x);
			//ps.setObject(1, objs);
			for (int k = 0; k < obj.length; k++) {
				 ps.setObject(k+1, obj[k]);
			}
			cnt = ps.executeUpdate();
			System.out.println("ִ�н��:"+cnt);
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cnt;
	}
}
