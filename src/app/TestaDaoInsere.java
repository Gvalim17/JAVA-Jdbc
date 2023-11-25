package app;

import dao.ContactDao;
import model.Contact;

import java.sql.SQLException;

public class TestaDaoInsere {
    public static void main(String[] args) {
        Contact Contact = new Contact();
        Contact.setName("Dudu");
        Contact.setEmail("dudu@gmail.com");
        Contact.setAddress("Dicionario contados, burro casa 02");

        try {
            ContactDao dao = new ContactDao();
            dao.createContact(Contact);
            System.out.println("Gravação feita no Banco de Dados!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
