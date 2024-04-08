package org.web.server.web_server_on_servlet.integration_tests;

import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.web.server.web_server_on_servlet.dao.DbConnector;

import java.sql.Connection;

@DataJpaTest
public class ServletTest {

    private EmbeddedDatabase embeddedDatabase;

    private Connection connection;

    private MockedStatic<DbConnector> dbConnector;

    @BeforeEach
    void setup() {
        try {
            embeddedDatabase = H2Connector.createInMemoryDatabase();
            connection = embeddedDatabase.getConnection();

            dbConnector = Mockito.mockStatic(DbConnector.class);
            Mockito.when(DbConnector.connectionDB()).thenReturn(connection);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        dbConnector.close();
        if (embeddedDatabase != null) {
            embeddedDatabase.shutdown();
        }
    }
}