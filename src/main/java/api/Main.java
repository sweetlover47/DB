package api;

import infrastructure.ui.ChooseRole;
import org.flywaydb.core.Flyway;
import repository.RepositoryPostgres;
import service.Service;

import java.awt.*;
import java.io.IOException;

public class Main {
    private static final String url = /*"jdbc:postgresql://localhost:54325/postgres"; */ "jdbc:postgresql://localhost:5432/travelagency";
    private static final String user = /*"postgres"; */ "nastya";
    private static final String password = /*"mysecretpassword"; */ "123456";

    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static void main(String[] args) throws IOException {
        Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
        flyway.migrate();
        new ChooseRole(new Controller(new Service(new RepositoryPostgres())));
    }
}