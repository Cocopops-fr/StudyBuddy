package com.studybuddy.interactions.web;


	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.RestController;

	@RestController
	public class HealthController {

	    @GetMapping("/api/interactions/health")
	    public String health() {
	        return "OK - interactions service alive";
	    }
	}



