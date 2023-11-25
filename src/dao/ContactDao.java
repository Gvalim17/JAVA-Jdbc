package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import controller.ConnectionFactory;
import model.Contact;

public class ContactDao {

	private Connection con;

	public ContactDao() throws SQLException {
		this.con = ConnectionFactory.getConnection();
	}

	public void createContact(Contact contact) throws SQLException {
		String sql = "insert into contacts (name, email, Address) values (?, ?, ?)";
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, contact.getName());
			stmt.setString(2, contact.getEmail());
			stmt.setString(3, contact.getAddress());
			stmt.execute();
			stmt.close();
			con.close();
	}

	public void removeContact(Long id) throws SQLException {
		String sql = "delete from contacts where id=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);
		stmt.execute();
		stmt.close();
		con.close();
	}
	
	public void updateContact(Contact contact) throws SQLException {
	    String sql = "update contacts set name = ?, email = ?, address = ? where id = ?";
	        PreparedStatement stmt = con.prepareStatement(sql);
	        stmt.setString(1, contact.getName());
	        stmt.setString(2, contact.getEmail());
	        stmt.setString(3, contact.getAddress());
	        stmt.setLong(4, contact.getId());
	        stmt.executeUpdate();
	        stmt.close();
			con.close();
	}



	public Contact getContactById(Long id) throws SQLException {
		String sql = "select * from contacts where id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setLong(1, id);
		ResultSet rset = stmt.executeQuery();
		Contact contact = null;

		while (rset.next()) {
			contact = new Contact();
			contact.setId(rset.getLong("id"));
			contact.setName(rset.getString("name"));
			contact.setEmail(rset.getString("email"));
			contact.setAddress(rset.getString("address"));
		}

		rset.close();
		stmt.close();
		return contact;
	}


	public List<Contact> getListContacts() throws SQLException {
		String sql = "select * from contacts";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rset = stmt.executeQuery();

		List<Contact> contacts = new ArrayList<Contact>();

		while (rset.next()) {
			Contact contact = new Contact();
			contact.setId(rset.getLong("id"));
	        contact.setName(rset.getString("name"));
	        contact.setEmail(rset.getString("email"));
	        contact.setAddress(rset.getString("address"));
			contacts.add(contact);
		}
		rset.close();
		stmt.close();
		con.close();

		return contacts;

	}
	
	public List<Contact> getContactsByLetter(String letter) throws SQLException {
	    String sql = "select * from contacts where name like ?";
	    PreparedStatement stmt = con.prepareStatement(sql);
	    
	    stmt.setString(1, letter + "%");

	    ResultSet rset = stmt.executeQuery();

	    List<Contact> contacts = new ArrayList<>();
	    while (rset.next()) {
			Contact contact = new Contact();
			contact.setId(rset.getLong("id"));
	        contact.setName(rset.getString("name"));
	        contact.setEmail(rset.getString("email"));
	        contact.setAddress(rset.getString("address"));
	        contacts.add(contact);
	    }
	    rset.close();
	    stmt.close();
		con.close();
	    return contacts;
	}




}