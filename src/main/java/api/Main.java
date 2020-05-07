package api;

import infrastructure.ui.ChooseRole;
import infrastructure.ui.tourist.PlanRestTrip;
import org.flywaydb.core.Flyway;
import repository.RepositoryPostgres;
import service.Service;

import java.awt.*;
import java.io.IOException;

public class Main {
    private static final String url = "jdbc:postgresql://localhost:5432/travelagency";
    private static final String user = "nastya";
    private static final String password = "123456";

    public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    public static void main(String[] args) throws IOException {
       // Flyway flyway = Flyway.configure().dataSource(url, user, password).load();
       // flyway.migrate();
        new ChooseRole(new Controller(new Service(new RepositoryPostgres())));

        //Controller c = new Controller(new Service(new RepositoryPostgres()));//.addNewCargoTrip(26L, "5", "russia", new Timestamp(0), new Timestamp(200));
        //c.setToCacheJoinedExcursions(8L);
        //c.setToCacheJoinedExcursions(1L);
        //c.setToCacheJoinedExcursions(3L);
        //new PlanRestTrip(c, c.getFromCacheJoinedExcursions());
        /*tourist tourist = new tourist();
        tourist.setAge(10);
        tourist.setName("Kirill");
        tourist.setSex("M");
        tourist.setPassport("3312122");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("oh.my");
        EntityManager em = entityManagerFactory.createEntityManager();

        Excursion exc = new Excursion();
        exc.setId(10);
        exc.setParticipatingTourists(new ArrayList<>());
        ArrayList<Excursion> excursions = new ArrayList<>();
        excursions.add(exc);

        tourist.setExcursions(excursions);
        em.getTransaction().begin();
        em.merge(tourist);
        em.getTransaction().commit();
        em.close();*/

    }
}


/*
BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("out.txt")));
        BufferedWriter out = new BufferedWriter(new FileWriter(new File("out1.txt")));
        int r;
        int i = 0;
        out.write("(");
        while ((r = bufferedReader.read()) != -1) {
            if (String.valueOf((char) r).equals("\r")) {
               out.write("),");
            }
            /*else if (r == '\t') {
                i++;                                    //timestamp
                out.write(',');
            }
            if (r == '\r') {
                out.write("),");
                i = 0;                                  //timestamp
            }
            else if (r == '\n')
                    out.write("\n(");
                    else
                    out.write(r);
                    }
                    out.write(");");
                    out.flush();
                    out.close();
*/