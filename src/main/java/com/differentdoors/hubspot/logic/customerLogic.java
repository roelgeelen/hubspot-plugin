package com.differentdoors.hubspot.logic;

import com.differentdoors.hubspot.models.Customer;

public class customerLogic {

    public Customer generateCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("henk");
        return customer;
    }
}
