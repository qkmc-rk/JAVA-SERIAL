package org.cat.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ReadAndInsertData {
	public static Connection conn = null;
	public static boolean getConnection(String ip,String port,String database,String username,String pwd){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://"+ip+":"+port+"/"+database;
			conn = DriverManager.getConnection(url, username, pwd);
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			JOptionPane.showMessageDialog(null, "连接数据库失败!");
			e.printStackTrace();
		}
		return false;
	}
	//插入温度和湿度的一条记录
	public boolean insertTandH(double temp,double hum){
		
		String sql1 = "create table if not Exists tempandhum ('data_time' timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,'temp' double(12,0) DEFAULT NULL,'hum' double(12,0) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci";
		String sql2 = "insert into tempandhum(temp,hum)values(?,?)";
		try {
			//这两句代码用于创建数据表
			PreparedStatement pstmt = conn.prepareStatement(sql1);
			pstmt.executeUpdate();
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setDouble(1, temp);
			pstmt.setDouble(2, hum);
			int x = pstmt.executeUpdate();
			if(x == 1) return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	//获取是否加热的标志
	public int getHitFlag(){
		String sql = "select * from isHit";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			int isHit = 0;
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				isHit = rs.getInt(1);
			}
			return isHit;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
