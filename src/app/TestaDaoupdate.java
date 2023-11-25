package app;

import java.sql.SQLException;

import dao.ContactDao;
import model.Contact;

public class TestaDaoupdate {
    public static void main(String[] args) throws SQLException {
        ContactDao dao = new ContactDao();

        // 1. Obtenha o contato existente
        Contact contactToUpdate = dao.getContactById((long) 7);

        if (contactToUpdate != null) {
            // 2. Atualize os dados do contato
            contactToUpdate.setName("jaria4");
            contactToUpdate.setEmail("jaria@gmail.com");
            contactToUpdate.setAddress("Av. Brasil3, 1000");

            // 3. Chame o método updateContact
            dao.updateContact(contactToUpdate);

            // 4. Adicione lógica de teste (opcional)
            System.out.println("Atualização feita no Banco de Dados!");
        } else {
            System.out.println("Contato não encontrado.");
        }
    }
}
