package service.api.driver.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import service.api.driver.dto.DriverDto;
import service.api.driver.entity.Driver;
import service.api.driver.repository.DriverRepository;
import service.api.driver.service.DriverService;

@Service
public class DriverServiceImpl implements DriverService{

	@Autowired
	private DriverRepository driverRepo;

	@Override
	public Driver updateStatus(DriverDto dto) {
		// TODO Auto-generated method stub
		Driver data = driverRepo.findById(Long.valueOf(dto.getDriver_id())).get();
		data.setStatus(dto.getStatus());
		driverRepo.save(data);
		//List<Object[]> listObj = driverRepo.findByDriveId(dto.getDriver_id());
		System.out.println("cek lat : "+ data.getLat());
		return data;
	}

	@Override
	public String findStatus(String driverId) {
		// TODO Auto-generated method stub
		String status = driverRepo.findStatus(driverId);
		return status;
	}

	@Override
	public List<Driver> getAllData() {
		// TODO Auto-generated method stub
		//List<Driver> listData = driverRepo.findAll();
		/*for(int i = 0; i < listData.size(); i++) {
			Driver data = new Driver();
			data.setId(listData.get(i).getId());
			//data.setLat(lat);
		}*/
		
		return driverRepo.findAll();
	}
}
