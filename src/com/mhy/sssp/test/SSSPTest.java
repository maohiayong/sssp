package com.mhy.sssp.test;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SSSPTest {

	private ApplicationContext ctx;
	
	public SSSPTest(){
		ctx=new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());
	}
}
