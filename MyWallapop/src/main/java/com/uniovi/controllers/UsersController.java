package com.uniovi.controllers;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.uniovi.entities.User;
import com.uniovi.services.RolesService;
import com.uniovi.services.SecurityService;
import com.uniovi.services.UsersService;
import com.uniovi.validators.SignUpFormValidator;



@Controller
public class UsersController {
	
	@Autowired
	private UsersService usersService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private SignUpFormValidator signUpFormValidator;
	

	@Autowired
	private RolesService rolesService;
	
	@Autowired
	private HttpSession httpSession;
	
	//GESTION DE LOGIN/REGISTRO

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute @Validated User user, BindingResult result) {
		
		
		signUpFormValidator.validate(user, result);
		if (result.hasErrors()) {
			return "signup";
		}
		user.setRole(rolesService.getRoles()[0]);
		user.setMoney(100.0);
		usersService.addUser(user);
		securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());
		return "redirect:user/home";
	}
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		model.addAttribute("user", new User());
		User activeUser = getActiveUser();
		httpSession.setAttribute("activeUser",activeUser);
		return "signup";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {

		return "login";
	}

	

	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		httpSession.setAttribute("activeUser",activeUser);

		return "home";
	}
	
	//GESTION DE USUARIOS
	
	@RequestMapping(value = "/user/home")
	public String getUserHome(Model model) {
		model.addAttribute("rolesList", rolesService.getRoles());
		User activeUser = getActiveUser();
		model.addAttribute("user", activeUser);
		return "user/home";
	}

	private User getActiveUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User activeUser = usersService.getUserByEmail(email);
		return activeUser;
	}
	
	@RequestMapping(value = "/user/add")
	public String getUser(Model model) {
		//TODO comprobar que no haga falta la userlist
		model.addAttribute("rolesList", rolesService.getRoles());
		return "user/add";
	}

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	public String setUser(@ModelAttribute User user) {
		usersService.addUser(user);
		return "redirect:/user/list";
	}
	 
	@RequestMapping(value = "/user/list/delete" , method = RequestMethod.POST )
	public String deleteUsersChecked(@RequestParam(value = "uid", required = false) String[] ids) {
		
		if (ids != null)
	    for (int i = 0; i < ids.length; i++) {
	    	Long uid = (long) Integer.parseInt( ids[i]);
	    	usersService.deleteUser(uid);
		}

	    return "redirect:/user/list";
	}
	
	@RequestMapping("/user/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
		usersService.deleteUser(id);
		return "redirect:/user/list";
	}
	
	@RequestMapping(value = "/user/list")
	public String getLista(Model model) {
		model.addAttribute("usersList", usersService.getUsers());
		boolean myBooleanVariable = false;
	    model.addAttribute("myBooleanVariable", myBooleanVariable);
		return "user/list";
	}
	
}