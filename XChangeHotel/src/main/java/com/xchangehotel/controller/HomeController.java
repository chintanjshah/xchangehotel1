package com.xchangehotel.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xchangehotel.model.User;
import com.xchangehotel.service.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController{
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! the client locale is "+ locale.toString());
		
		return "Login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String submitLogin(Locale locale, Model model, @RequestParam("username") String username, @RequestParam("password") String password) {
		logger.info("login locale is "+ locale.toString());

		try
		{
			User user = new User();
			
			user.setUsername(username);
			user.setPassword(password);
			user.setApiKey("0d445a36ea047b8a7e7e6903");
			user.setApiSecret("634d7a7e217813d382bcf8aa");
			
			String response = userService.getLogin(user);
			System.out.println("response:=>"+response);
			model.addAttribute("message", response);
			
		}catch(Exception e)
		{
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "Login";
		}
		
		return "Home";
	}
	
    @Autowired
    private UserService userService;
	
}
