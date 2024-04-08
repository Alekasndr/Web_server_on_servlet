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
    private static EmbeddedDatabase embeddedDatabase;

    private static Connection connection;

    @BeforeEach
    void setup() {
        try {
            embeddedDatabase = H2Connector.createInMemoryDatabase();
            connection = embeddedDatabase.getConnection();
            try (MockedStatic<DbConnector> dbConnectorMockedStatic = Mockito.mockStatic(DbConnector.class)) {
                dbConnectorMockedStatic.when(DbConnector::connectionDB).thenReturn(connection);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterEach
    void tearDown() {
        if (embeddedDatabase != null) {
            embeddedDatabase.shutdown();
        }
    }
}