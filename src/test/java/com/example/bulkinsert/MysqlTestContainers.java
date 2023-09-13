package com.example.bulkinsert;

import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.MySQLContainer;

@Configuration
public class MysqlTestContainers {
    private static final String MYSQL_DOCKER_IMAGE = "mysql";
    public static final Integer MYSQL_PORT = 3306;

    static {
        final String dbName = "dbName";
        final String userName = "userName";
        final String password = "password";

        var mysqlContainer = new MySQLContainer<>(MYSQL_DOCKER_IMAGE)
                .withDatabaseName(dbName)
                .withUsername(userName)
                .withPassword(password)
                .withExposedPorts(MYSQL_PORT);

        mysqlContainer.start();

        System.setProperty(
                "MYSQL_HOST",
                mysqlContainer.getHost()
        );
        System.setProperty(
                "MYSQL_PORT",
                String.valueOf(mysqlContainer.getMappedPort(MYSQL_PORT))
        );
        System.setProperty(
                "DB_NAME",
                dbName
        );
        System.setProperty(
                "USER_NAME",
                userName
        );
        System.setProperty(
                "PASSWORD",
                password
        );
    }
}
