package bank.dao;

import bank.model.Customer;

public class DbDump {
    public static void main(String[] args) throws Exception {
        Database.createTables();
        for (Customer c : new CustomerDAO().findAll()) {
            System.out.println(c.getCustomerId() + "  " + c.getFirstName() + " " + c.getSurname());
        }
        System.out.println("done");
    }
}