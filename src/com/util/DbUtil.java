package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {
	public  Connection getCon() throws Exception{
		Class.forName(PropertiesUtil.getValue("jdbcName"));
		Connection con=DriverManager.getConnection(PropertiesUtil.getValue("dbUrl"), PropertiesUtil.getValue("dbUserName"), PropertiesUtil.getValue("dbPassword"));
	    return con;
	
	}
	public void closeCon(Connection con) throws Exception{
		if(con!=null){
			con.close();
		}
	}

}
