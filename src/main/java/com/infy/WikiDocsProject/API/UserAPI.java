package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Service.ArticleService;
import com.infy.WikiDocsProject.Service.CustomUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 
 * User API Controller Class
 *
 */
@SuppressWarnings("UnnecessaryLocalVariable")
@RestController
@CrossOrigin
@RequestMapping("UserAPI")
public class UserAPI {

	private final CustomUserDetailsServiceImpl customUserDetailsService;
	private final ArticleService articleService;

	/**
	 * Constructor using constructor injection
	 */
	@Autowired
	public UserAPI(CustomUserDetailsServiceImpl customUserDetailsService, ArticleService articleService) {
		this.customUserDetailsService = customUserDetailsService;
		this.articleService = articleService;
	}

	/**
	 * Retrieve a users name by their given email
	 * @param email the email of the user requested
	 * @return the name of the user
	 */
	@GetMapping("getNameByEmail/{email:.+}")
	@ResponseBody
	public String getNameByEmail(@PathVariable String email){
		String name = customUserDetailsService.getNameByEmail(email);
		return name;
	}

}
