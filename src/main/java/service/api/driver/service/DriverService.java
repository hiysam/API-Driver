package service.api.driver.service;

import java.util.List;

import service.api.driver.dto.DriverDto;
import service.api.driver.entity.Driver;

public interface DriverService {

	Driver updateStatus(DriverDto dto);

	String findStatus(String driverId);

	List<Driver> getAllData();

}
