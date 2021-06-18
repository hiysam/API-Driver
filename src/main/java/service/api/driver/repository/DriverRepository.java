package service.api.driver.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import service.api.driver.entity.Driver;

public interface DriverRepository extends JpaRepository<Driver, Long>{

	@Query(value="SELECT Status FROM driver WHERE id = :driverId", nativeQuery=true)
	String findStatus(@Param("driverId") String driverId);

	/*List<Object[]> findByDriveId(String driver_id);
	
	@Transactional
	@Modifying
	@Query(
			value="UPDATE driver SET "
					+ "Status = :status "
					+ "where Id = :id",
			nativeQuery=true)
	void updateStatus(@Param("id")String driver_id, @Param("status") String status);*/

}
