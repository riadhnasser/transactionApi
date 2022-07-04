package com.example.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {

	@GetMapping("/")
    public String index(Model model) {
		String version = System.getenv("APP_VERSION");
		if(version == null) version = "Default Version";
		model.addAttribute("target", version);
		return "index";
    }

}
