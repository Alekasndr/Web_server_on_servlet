package org.web.server.web_server_on_servlet.integration_tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.web.server.web_server_on_servlet.dao.AddressDAOImp;
import org.web.server.web_server_on_servlet.dao.DbConnector;
import org.web.server.web_server_on_servlet.dao.PassportDAOImp;
import org.web.server.web_server_on_servlet.dao.UserDAOImp;

import java.sql.Connection;
import java.sql.SQLException;


@DataJpaTest
public class YourRepositoryTest {
    private static EmbeddedDatabase embeddedDatabase;

    @Autowired
    private static TestEntityManager entityManager;

    private static Connection connection;

    @InjectMocks
    private UserDAOImp userDAOImp = new UserDAOImp(new AddressDAOImp(), new PassportDAOImp());

    @BeforeAll
    public static void setup() {
        try {
            embeddedDatabase = H2Connector.createInMemoryDatabase();
            connection = embeddedDatabase.getConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void tearDown() {
        if (embeddedDatabase != null) {
            embeddedDatabase.shutdown();
        }
    }

    @Test
    public void shouldSaveAndFindUser() throws SQLException {
        try (MockedStatic<DbConnector> dbConnectorMockedStatic = Mockito.mockStatic(DbConnector.class)) {
            dbConnectorMockedStatic.when(DbConnector::connectionDB).thenReturn(connection);
        }
        userDAOImp.getByEmail("First");
    }

}