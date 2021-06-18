package service.api.driver.entity;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "driver")
public class Driver {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "Lat")
	private Double lat;
	
	@Column(name = "Lon")
	private Double lon;
	
	@Column(name = "Status")
	private String status;

	public Long getId() {
		return id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public JSONObject toJSON() {
		// TODO Auto-generated method stub
		JSONObject driver = new JSONObject();
		DecimalFormat df = new DecimalFormat(".00");
		driver.put("id", id);
		driver.put("lat", df.format(lat));
		driver.put("lon", df.format(lon));
		driver.put("status", status);
		return driver;
	}
}
