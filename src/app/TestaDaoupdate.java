package app;

import dao.ContactDao;
import model.Contact;

import java.sql.SQLException;

public class TestaDaoupdate {
    public static void main(String[] args) throws SQLException {
        ContactDao dao = new ContactDao();

        Contact contactToUpdate = dao.getContactById((long) 7);

        if (contactToUpdate != null) {

            contactToUpdate.setName("jaria4");
            contactToUpdate.setEmail("jaria@gmail.com");
            contactToUpdate.setAddress("Av. Brasil3, 1000");

            dao.updateContact(contactToUpdate);

            System.out.println("Atualização feita no Banco de Dados!");
        } else {
            System.out.println("Contato não encontrado.");
        }
    }
}
