package app;

import dao.ContactDao;
import model.Contact;

import java.sql.SQLException;

public class TestaGetId {
    public static void main(String[] args) {
        try {
            ContactDao dao = new ContactDao();
            Contact contact = dao.getContactById((long) 2);
            if (contact != null) {
                System.out.println("Nome: " + contact.getName());
                System.out.println("E-mail: " + contact.getEmail());
                System.out.println("Endereço: " + contact.getAddress());
            } else {
                System.out.println("erro");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
