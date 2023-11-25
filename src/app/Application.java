package app;

import dao.ContactDao;
import model.Contact;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.awt.Color;


public class Application {
    private static DefaultTableModel tableModel;
    private static JTable contactsTable;

    public static JFrame app() {
        JFrame window = new JFrame("Atualização de Cliente");
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(600, 500);

        Container box = window.getContentPane();
        box.setLayout(null);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Email");
        tableModel.addColumn("Address");

        contactsTable = new JTable(tableModel);
        contactsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane scrollPane = new JScrollPane(contactsTable);
        scrollPane.setBounds(10, 10, 565, 300);
        box.add(scrollPane);
        int[] columnWidths = {50, 120, 195, 195};

        for (int i = 0; i < contactsTable.getColumnCount(); i++) {
            TableColumn column = contactsTable.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
        // Search
        JLabel search = new JLabel("Search Contacts");
        search.setBounds(10, 325, 120, 20);
        box.add(search);
        JTextField searchId = new JTextField();
        searchId.setBounds(10, 350, 40, 20);
        box.add(searchId);
        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(60, 350, 90, 20);
        box.add(btnSearch);

        // Save
        JLabel create = new JLabel("Create Contact");
        create.setBounds(180, 325, 120, 20);
        box.add(create);
        JLabel name = new JLabel("Name");
        name.setBounds(180, 350, 50, 20);
        box.add(name);
        JLabel email = new JLabel("Email");
        email.setBounds(180, 375, 50, 20);
        box.add(email);
        JLabel address = new JLabel("Address");
        address.setBounds(180, 400, 50, 20);
        box.add(address);
        JTextField createName = new JTextField();
        createName.setBounds(240, 350, 200, 20);
        box.add(createName);
        JTextField createEmail = new JTextField();
        createEmail.setBounds(240, 375, 200, 20);
        box.add(createEmail);
        JTextField createAddress = new JTextField();
        createAddress.setBounds(240, 400, 200, 20);
        box.add(createAddress);
        JButton btnSave = new JButton("Save");
        btnSave.setBounds(300, 325, 80, 20);
        box.add(btnSave);

        // Delete
        JLabel delete = new JLabel("Delete Contact");
        delete.setBounds(460, 325, 120, 20);
        box.add(delete);
        JLabel deleteId = new JLabel("ID");
        deleteId.setBounds(460, 350, 40, 20);
        box.add(deleteId);
        JTextField id = new JTextField();
        id.setBounds(480, 350, 40, 20);
        box.add(id);
        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(460, 375, 90, 20);
        box.add(btnDelete);
        JButton btnDeleteAll = new JButton("Delete All");
        btnDeleteAll.setBackground(Color.RED);
        btnDeleteAll.setForeground(Color.WHITE);
        btnDeleteAll.setBounds(460, 425, 90, 20);
        box.add(btnDeleteAll);


        // Clear
        JButton btnClear = new JButton("Clear");
        btnClear.setBounds(60, 375, 90, 20);
        box.add(btnClear);

        window.setVisible(true);

        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String x = searchId.getText().trim();
                ContactDao dao;

                try {
                    dao = new ContactDao();

                    if (x.isEmpty()) {
                        List<Contact> allContacts = dao.getListContacts();
                        displayContacts(allContacts);

                    } else if (containsOnlyLetters(x)) {
                        List<Contact> contactsByLetter = dao.getContactsByLetter(x);
                        displayContacts(contactsByLetter);

                    } else if (containsOnlyNumbers(x)) {
                        long y = Long.parseLong(x);
                        Contact contact = dao.getContactById(y);
                        searchId.setEnabled(false);
                        btnSearch.setEnabled(false);

                        if (contact != null) {
                            createName.setText(contact.getName());
                            createEmail.setText(contact.getEmail());
                            createAddress.setText(contact.getAddress());
                            displayContact(contact);
                        } else {
                            searchId.setEnabled(true);
                            btnSearch.setEnabled(true);
                            searchId.setText("");
                            searchId.requestFocus();
                            JOptionPane.showMessageDialog(null,"ID not found", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();

                }
            }
        });



        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String x = createName.getText();
                String y = createEmail.getText();
                String z = createAddress.getText();
                String id = searchId.getText();

                if (id.isEmpty()) {
                    if (!x.isEmpty() && !y.isEmpty()) {
                        ContactDao dao = null;
                        try {
                            dao = new ContactDao();
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        Contact newContact = new Contact();
                        newContact.setName(x);
                        newContact.setEmail(y);
                        newContact.setAddress(z);
                        try {
                            dao.createContact(newContact);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        createName.setText("");
                        createEmail.setText("");
                        createAddress.setText("");
                    } else {
                        JOptionPane.showMessageDialog(window, "Name and Email are required for creating a new contact.",
                                "Validation Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    int question = JOptionPane.showConfirmDialog(window, "Do you want to update??", "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (question == JOptionPane.YES_OPTION) {
                        try {
                            long idL = Long.parseLong(id);
                            ContactDao dao1 = new ContactDao();
                            Contact contact1 = dao1.getContactById(idL);
                            if (contact1 != null) {
                                contact1.setName(x);
                                contact1.setEmail(y);
                                contact1.setAddress(z);
                                dao1.updateContact(contact1);

                            }
                            createName.setText("");
                            createEmail.setText("");
                            createAddress.setText("");
                            btnSearch.setEnabled(true);
                            searchId.setEnabled(true);
                            searchId.setText("");

                        } catch (NumberFormatException | SQLException e1) {
                            e1.printStackTrace();
                        }

                    }
                }
                try {
                    ContactDao dao = new ContactDao();
                    List<Contact> allContacts = dao.getListContacts();
                    displayContacts(allContacts);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchId.setText("");
                createName.setText("");
                createEmail.setText("");
                createAddress.setText("");
                btnSearch.setEnabled(true);
                searchId.setEnabled(true);


                try {
                    ContactDao dao = null;
                    dao = new ContactDao();
                    List<Contact> allContacts = dao.getListContacts();
                    displayContacts(allContacts);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        btnDeleteAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    ContactDao dao = new ContactDao();
                    int question1 = JOptionPane.showConfirmDialog(window, "Do you want Delete ALL ??", "Confirm",
                            JOptionPane.YES_NO_OPTION);
                    if (question1 == JOptionPane.YES_OPTION) {
                        int question2 = JOptionPane.showConfirmDialog(window, "Do you sure about that ??", "Confirm",
                                JOptionPane.YES_NO_OPTION);
                        if (question2 == JOptionPane.YES_OPTION) {
                            int question3 = JOptionPane.showConfirmDialog(window, "Really man? Will do it without a Where ??", "Confirm",
                                    JOptionPane.YES_NO_OPTION);
                            if (question3 == JOptionPane.YES_OPTION) {
                                dao.removeAllContacts();
                            }
                        }

                    }

                    dao = new ContactDao();
                    List<Contact> allContacts = dao.getListContacts();
                    displayContacts(allContacts);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String x = id.getText().trim();
                ContactDao dao;
                try {
                    dao = new ContactDao();
                    long y = Long.parseLong(x);
                    dao.removeContact(y);


                    id.setText("");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                try {
                    dao = new ContactDao();
                    List<Contact> allContacts = dao.getListContacts();
                    displayContacts(allContacts);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        return window;
    }



    private static void displayContacts(List<Contact> contacts) {
        clearTable();
        for (Contact contact : contacts) {
            Object[] rowData = {contact.getId(), contact.getName(), contact.getEmail(), contact.getAddress()};
            tableModel.addRow(rowData);
        }
    }

    private static void displayContact(Contact contact) {
        clearTable();
        if (contact != null) {
            Object[] rowData = {contact.getId(), contact.getName(), contact.getEmail(), contact.getAddress()};
            tableModel.addRow(rowData);
        }
    }

    private static void clearTable() {
        tableModel.setRowCount(0);
    }

    private static boolean containsOnlyLetters(String texto) {
        return texto.matches("[a-zA-Z]+");
    }

    private static boolean containsOnlyNumbers(String texto) {
        return texto.matches("\\d+");
    }

    public static void main(String[] args) {
        app();
        ContactDao dao = null;
        try {
            dao = new ContactDao();
            List<Contact> allContacts = dao.getListContacts();
            displayContacts(allContacts);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
