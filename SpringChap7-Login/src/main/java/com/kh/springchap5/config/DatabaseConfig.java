package com.kh.springchap5.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DatabaseConfig {
	
	// DataSourceInitializer 객체는 데이터베이스 초기화를 수행할 수 있는
	// 데이터 소스와 팝렐이터 설정해주는 것이 작성되어 있음
	// ResourceDatabasePopulator : 데이터베이스를 초기화 하는데 사용
	// addScript 데이터베이스를 초기화하고 추가할 스크립트 파일을 추가
	// sql/create-members-table.sql sql에 추가할 DDL 문을 작성해서 넣어
	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		DataSourceInitializer initializer = new DataSourceInitializer();
		initializer.setDataSource(dataSource);
		initializer.setDatabasePopulator(databasePopulator(dataSource));
		return initializer;
	}
	private ResourceDatabasePopulator databasePopulator(DataSource dataSource) {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		// 만약에 테이블이 없을 경우에만 생성하게 만들자!
		if(!tableExistis(dataSource, "Members")) {
			populator.addScript(new ClassPathResource("sql/create-members-table.sql"));	
		}
		
		
		return populator;
	}
	private boolean tableExistis(DataSource dataSourc, String tableName) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSourc);
		String query = "SELECT COUNT(*) FROM ALL_TABLES WHERE TABLE_NAME = UPPER(?)";
		int count = jdbcTemplate.queryForObject(query, Integer.class, tableName.toUpperCase());
		return count > 0;
	}
}
