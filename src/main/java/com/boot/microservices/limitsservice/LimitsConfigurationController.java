package com.boot.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.microservices.limitsservice.bean.LimitsConfiguration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class LimitsConfigurationController {
	
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	public LimitsConfiguration getLimitsConfiguration(){
		return new LimitsConfiguration(configuration.getMaximum(), configuration.getMinimum());
	}
	
	@GetMapping("/limits-hystrix")
	@HystrixCommand(fallbackMethod = "getDefaultLimits")
	public LimitsConfiguration getLimitsConfigurationFaultTolerant(){
		throw new RuntimeException("Unable to connect. "
				+ "You should,still be good for now "
				+ "as you have Hystrix configured to be fault tolerant!");
	}
	
	private LimitsConfiguration getDefaultLimits() {
		return new LimitsConfiguration(444, 42);
	}
}
