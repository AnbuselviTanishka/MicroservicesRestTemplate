package com.example.employeeservice;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class EmployeeService {
	 @Autowired
	    private EmployeeRepo employeeRepo;
	 
	 @Autowired
	 private WebClient webClient;
	 
	    @Autowired
	    private ModelMapper mapper;
	    
	    @Autowired
	    private RestTemplate restTemplate;
	    
	 
	    public EmployeeResponse getEmployeeById(int id) {
	        Optional<Employee> employee = employeeRepo.findById(id);
	        ModelMapper modelMapper = new ModelMapper();
	        System.out.println(modelMapper.map(employee, EmployeeResponse.class));

	        EmployeeResponse employeeResponse = mapper.map(employee, EmployeeResponse.class);
	      //AddressResponse addressResponse = restTemplate.getForObject("http://localhost:8096/address/{id}", AddressResponse.class, id);
		   //employeeResponse.setAddressResponse(addressResponse);
	        AddressResponse addressResponse = webClient.get().uri("/address/" + id).retrieve().bodyToMono(AddressResponse.class).block();
	        employeeResponse.setAddressResponse(addressResponse);
	        return employeeResponse;
	    }
}
