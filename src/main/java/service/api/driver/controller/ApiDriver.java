package service.api.driver.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import service.api.driver.dto.DriverDto;
import service.api.driver.entity.Driver;
import service.api.driver.service.DriverService;

@RestController
@RequestMapping(value = "/api")
public class ApiDriver {
	
	@Autowired
	private DriverService driverSvc;

	@GetMapping(value = "checkApi")
	public String checkApi() {
		return "oke";
	}
	
	@PostMapping(value = "aktifasiDriver")
	public void AktivasiDriver(@RequestBody DriverDto dto, HttpServletResponse json) throws IOException {
		JSONObject response = new JSONObject();
		if(!dto.getDriver_id().trim().isEmpty()) {
			String status = driverSvc.findStatus(dto.getDriver_id());
			if(status.equals("Off")) {
				Driver data = new Driver();
				dto.setStatus("Active");
				data = driverSvc.updateStatus(dto);
				response.put("rc", "00");
				response.put("rd", "Success");
				response.put("driver", data.toJSON());
			} else {
				response.put("rc", "02");
				response.put("rd", "Maaf tidak dapat merubah Status karena Status tidak Off");
			}
		} else {
			response.put("rc", "01");
			response.put("rd", "driver_id tidak boleh kosong");
		}
		
		json.setContentType("application/json");
		PrintWriter out = json.getWriter();
		out.print(response.toString());
		out.flush();
	}
	
	@PostMapping(value = "assignDriver")
	public void AssignDriver(@RequestBody DriverDto dto, HttpServletResponse json) throws IOException {
		JSONObject response = new JSONObject();
		if(!dto.getLat().trim().isEmpty() && !dto.getLon().trim().isEmpty()) {
			List<Driver> listData = new ArrayList<Driver>();
			listData = driverSvc.getAllData();
			
			double[] lat = new double[listData.size()];
			double[] lon = new double[listData.size()];
			for(int i = 0; i < listData.size(); i++) {
				lat[i] = listData.get(i).getLat();
				lon[i] = listData.get(i).getLon();
			}
			
			double searchLat = Double.parseDouble(dto.getLat());
			double searchLon = Double.parseDouble(dto.getLon());
			
			int getIndex = CariNilaiTerdekat(lat, searchLat, lon, searchLon);
			Driver data = new Driver();
			data.setId(Long.valueOf(listData.get(getIndex).getId()));
			data.setLat(listData.get(getIndex).getLat());
			data.setLon(listData.get(getIndex).getLon());
			data.setStatus(listData.get(getIndex).getStatus());
			
			response.put("rc", "00");
			response.put("rd", "Success");
			response.put("driver", data.toJSON());
		} else {
			response.put("rc", "01");
			response.put("rd", "lat atau lon tidak boleh kosong");
		}
		
		json.setContentType("application/json");
		PrintWriter out = json.getWriter();
		out.print(response.toString());
		out.flush();
	}
	
	@PostMapping(value = "deassignDriver")
	public void DeassignDriver(@RequestBody DriverDto dto, HttpServletResponse json) throws IOException {
		JSONObject response = new JSONObject();
		if(!dto.getDriver_id().trim().isEmpty()) {
			String status = driverSvc.findStatus(dto.getDriver_id());
			if(status.equals("OnJob")) {
				Driver data = new Driver();
				dto.setStatus("Active");
				data = driverSvc.updateStatus(dto);
				response.put("rc", "00");
				response.put("rd", "Success");
				response.put("driver", data.toJSON());
			} else {
				response.put("rc", "02");
				response.put("rd", "Maaf tidak dapat merubah Status karena Status tidak OnJob");
			}
		} else {
			response.put("rc", "01");
			response.put("rd", "driver_id tidak boleh kosong");
		}
		
		json.setContentType("application/json");
		PrintWriter out = json.getWriter();
		out.print(response.toString());
		out.flush();
	}
	
	@PostMapping(value = "deaktifasiDriver")
	public void DeaktifasiDriver(@RequestBody DriverDto dto, HttpServletResponse json) throws IOException {
		JSONObject response = new JSONObject();
		if(!dto.getDriver_id().trim().isEmpty()) {
			String status = driverSvc.findStatus(dto.getDriver_id());
			if(status.equals("Active")) {
				Driver data = new Driver();
				dto.setStatus("Off");
				data = driverSvc.updateStatus(dto);
				response.put("rc", "00");
				response.put("rd", "Success");
				response.put("driver", data.toJSON());
			} else {
				response.put("rc", "02");
				response.put("rd", "Maaf tidak dapat merubah Status karena Status tidak Active");
			}
		} else {
			response.put("rc", "01");
			response.put("rd", "driver_id tidak boleh kosong");
		}
		
		json.setContentType("application/json");
		PrintWriter out = json.getWriter();
		out.print(response.toString());
		out.flush();
	}
	
	@PostMapping(value = "daftarDriver")
	public void DaftarDriver(@RequestBody DriverDto dto, HttpServletResponse json) throws IOException {
		JSONObject response = new JSONObject();
		if(!dto.getDriver_id().trim().isEmpty()) {
			List<Driver> listData = new ArrayList<Driver>();
			List<DriverDto> listResponse = new ArrayList<DriverDto>();
			listData = driverSvc.getAllData();
			for(int i = 0; i < listData.size(); i++) {
				DecimalFormat df = new DecimalFormat(".00");
				DriverDto data = new DriverDto();
				data.setDriver_id(listData.get(i).getId().toString());
				data.setLat(df.format(listData.get(i).getLat()));
				data.setLon(df.format(listData.get(i).getLon()));
				data.setStatus(listData.get(i).getStatus());
				listResponse.add(data);
			}
			System.out.println("jumlah list : "+listData.size());
			JSONArray jsonArray = new JSONArray(listResponse);
			response.put("rc", "00");
			response.put("rd", "success");
			response.put("driver", jsonArray);
		} else {
			response.put("rc", "01");
			response.put("rd", "driver_id tidak boleh kosong");
		}
		
		json.setContentType("application/json");
		PrintWriter out = json.getWriter();
		out.print(response.toString());
		out.flush();
	}
	
	public static int CariNilaiTerdekat(double[] deret_lat, double searchLat, double[] deret_lon, double searchLon) {
		//int min = 0,
		//int max = deret_lat.length-1;
		int index = 0;
		 
		//double max_stat,
		//min_stat,
		//med_stat;
		 
		//double pos_a,
		//pos_b;
		double lowValueLat = 0;
		double lowValueLon = 0;
		
		for(int i = 0; i < deret_lat.length; i++) {
			//int median = (min + max) / 2;
			double valueLan = deret_lat[i]-searchLat;
			double valuLon = deret_lon[i]-searchLon;
			System.out.println("n : "+valueLan);
			System.out.println("y : "+valuLon);
			System.out.println("lowvalueN : "+lowValueLat);
			System.out.println("lowvalueY : "+lowValueLon);
			if(valueLan < lowValueLat && valuLon < lowValueLon || i == 0) {
				lowValueLat = Math.abs(valueLan);
				lowValueLon = Math.abs(valuLon);
				index = i;
				System.out.println("lowvalueN : "+lowValueLat);
				System.out.println("lowvalueY : "+lowValueLon);
			}
		}
		 
		/*while (min < max) {
			int median = (min + max) / 2;
			System.out.println("deret_n[median] : "+deret_n[median]);
			double left_search = deret_n[median]-search;
			double right_search = deret_n[median+1]-search;
			 
			pos_a = Math.abs(left_search);
			//System.out.println("cek pos_a : index "+min+" hasil : "+pos_a);
			pos_b = Math.abs(right_search);
			//System.out.println("cek pos_b : index "+min+" hasil : "+pos_b);
			int result = median+1;
			System.out.println("nilai post_a & post_b : "+ pos_a +" & "+pos_b);
			if(pos_b >= pos_a){
			   max = median;
			}
			else {
			   min = result;
			}
		}*/
		//med_stat = deret_lat[max];
		System.out.println("slesai value n : "+deret_lat[index]);
		System.out.println("slesai value y : "+deret_lon[index]);
		return index;
	}
}