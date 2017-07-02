package com.foresee.test.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {
	
	public static ResultSet query(Connection conn, String query){
		
		ResultSet rs = null;
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(query);
			
			rs = ps.executeQuery();
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return rs;
		
	}
	
	public static void update(Connection conn, String query){
		
//		ResultSet rs = null;
		
		try {
			
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.executeUpdate();
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
//		return rs;
		
	}



}
