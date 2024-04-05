package org.web.server.web_server_on_servlet.integration_tests;
import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

public class H2Connector {
    public static EmbeddedDatabase createInMemoryDatabase() throws Exception {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");

        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("test")
                .setScriptEncoding("UTF-8")
                .addScript("schema.sql") // Загрузите SQL скрипт с созданием таблиц
                .addScript("test_inser.sql")   // Загрузите SQL скрипт с данными для таблиц
                .build();
    }
}
