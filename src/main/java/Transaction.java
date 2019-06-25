import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class Transaction {

    // Hold a reusable reference to a SessionFactory (since we need only one)
    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml")
            .addAnnotatedClass(Client.class).buildSessionFactory();

    public static void main(String[] args) {

        Client c = new Client();

        c.setName("King Transaction");
        c.setId(6);

        update(c);
//        delete(c);
//        add(c);

        fetchAllClient().stream().forEach(System.out::println);

    }

    // delete a object in the db
    private static void delete(Client client) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to update the client
        session.delete(client);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    // update a object in the db
    private static void update(Client client){

        Session session = sessionFactory.openSession();

        session.beginTransaction();

        session.update(client);

        session.getTransaction().commit();

        session.close();

    }

    private static void add(Client c) {
        // Open a session
        Session session = sessionFactory.openSession();

        // Begin a transaction
        session.beginTransaction();

        // Use the session to update the contact
        session.save(c);

        // Commit the transaction
        session.getTransaction().commit();

        // Close the session
        session.close();
    }

    @SuppressWarnings("unchecked")
    private static List<Client> fetchAllClient() {
        // Open a session
        Session session = sessionFactory.openSession();

        // Create CriteriaQuery
        CriteriaQuery<Client> criteria = session.getCriteriaBuilder().createQuery(Client.class);
        criteria.from(Client.class);

        List<Client> clients = session.createQuery(criteria).getResultList();

        session.close();

        return clients;
    }

}
