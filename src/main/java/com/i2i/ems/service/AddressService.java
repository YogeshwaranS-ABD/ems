package com.i2i.ems.service;

import java.time.LocalDateTime;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.i2i.ems.exception.EmployeeManagementException;
import com.i2i.ems.dao.AddressDao;
import com.i2i.ems.model.Address;
import com.i2i.ems.model.Employee;
import com.i2i.ems.utils.Util;

public class AddressService {

    private static AddressDao addressDao = new AddressDao();
    private static Logger logger = LogManager.getLogger(AddressService.class);

    public Address createAddress(String[] addr, Employee employee)
                     throws EmployeeManagementException {
        logger.debug("Entered into createAddress to create new address");
        String id = Util.generateIdForAddress();
        Address address = new Address();
        address.setId(id);
        address.setDoorNo(addr[0]);
        address.setStreet(addr[1]);
        address.setArea(addr[2]);
        address.setCity(addr[3]);
        address.setPinCode(addr[4]);
        address.setEmployee(employee);
        address.setCreatedOn(LocalDateTime.now().toString());
        logger.info("The given address created successfully with address_id: {}", id);
        addressDao.addAddress(address);
        logger.info("A new record for the address with id: {} has created in database successfully", address.getId());
        return address;
    }

    public void update(Address address) throws EmployeeManagementException {
        logger.debug("Entered into update method to update the address with id: {}", address.getId());
        addressDao.updateAddress(address);
        logger.info("Address with id: {} updated successfully", address.getId());
    }

  /*  public Address getAddressWithId(String id) throws EmployeeManagementException {
        logger.debug("Enterd into getAddressWithID to retrive address with id: {} from the record", id);
        Address address = addressDao.getAddressWithId(id);
        logger.info("Address with id: {} was retrived from the record successfully", id);
        return address;
    }

    public boolean removeAddress(String id) throws EmployeeManagementException {
        logger.debug("Entered into removeAddress method to remove addres with id: {} from the record.", id);
        boolean result = addressDao.removeAddressWithId(id);
        logger.info("Address with id: {} was removed successfuly from the record.", id);
        return result;
    } */
}