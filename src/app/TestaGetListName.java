package app;

import dao.ContactDao;
import model.Contact;

import java.sql.SQLException;
import java.util.List;


public class TestaGetListName {
    public static void main(String[] args) {
        try {
            ContactDao cdao = new ContactDao();
            List<Contact> contacts = cdao.getContactsByLetter("d");


            for (Contact contact : contacts) {
                System.out.println("Nome: " + contact.getName());
                System.out.println("E-mail: " + contact.getEmail());
                System.out.println("Endereço: " + contact.getAddress());
                System.out.println("----------------------------");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
