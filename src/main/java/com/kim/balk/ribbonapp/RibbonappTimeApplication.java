package com.kim.balk.ribbonapp;

import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kim.balk.ribbonapp.config.RibbonTimeConfig;

@RestController
@EnableDiscoveryClient
@RibbonClient(name="gateway-service", configuration=RibbonTimeConfig.class)
@SpringBootApplication
public class RibbonappTimeApplication {

	@Inject
	private RestTemplate restTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(RibbonappTimeApplication.class, args);
	}

	@GetMapping
	public String getTime(){
		//return "getTime ->"+restTemplate.getForEntity("http://localhost:8011/", String.class).getBody();
		return restTemplate.getForEntity("http://gateway-service", String.class).getBody();
	}
	
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		 return builder.build();
	}
}
