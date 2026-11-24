package bank.dao;

import bank.model.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public void insert(Customer c) throws SQLException {
        String sql = "INSERT INTO customers "
        + "(customer_id, first_name, surname, address, phone_number, email)"
        + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Database.getConnection();
    PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, c.getCustomerId());
        ps.setString(2, c.getFirstName());
        ps.setString(3, c.getSurname());
        ps.setString(4, c.getAddress());
        ps.setString(5, c.getPhoneNumber());
        ps.setString(6, c.getEmail());
        ps.executeUpdate();
        }
    }

public Customer findById(String customerId) throws SQLException {
    String sql = "SELECT * FROM customers WHERE customer_id = ?";
    try (Connection con = Database.getConnection();
    PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setString(1, customerId);
        try (ResultSet rs = ps.executeQuery()) {
            return rs.next() ? map(rs) : null;
        }
    }
}

public List<Customer> findAll() throws SQLException {
    List<Customer> list = new ArrayList<>();
    String sql = "SELECT * FROM customers ORDER BY customer_id";
    try(Connection con = Database.getConnection();
    PreparedStatement ps = con.prepareStatement(sql);
    ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            list.add(map(rs));
        }
    }
    return list;
}

public void update (Customer c) throws SQLException {
    String sql = "UPDATE customers SET address = ?, phone_number = ?, email = " + "WHERE customer_d = ?";
    try(Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getAddress());
            ps.setString(2, c.getPhoneNumber());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getCustomerId());
            ps.executeUpdate();
        }
    }

    public void delete(String customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (Connection con = Database.getConnection();
        PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, customerId);
            ps.executeUpdate();
        }
    }

    private Customer map(ResultSet rs) throws SQLException {
        return Customer.restore(
        rs.getString("customer_id"),
        rs.getString("first_name"),
        rs.getString("surname"),
        rs.getString("address"),
        rs.getString("phone_number"),
        rs.getString("email"));

    }
}
