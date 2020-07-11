package org.kitchen.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.kitchen.domain.Criteria;
import org.kitchen.domain.PageDTO;
import org.kitchen.domain.RecipeVO;
import org.kitchen.domain.UserVO;
import org.kitchen.service.RecipeService;
import org.kitchen.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

   private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
   
   @Autowired
   private RecipeService recipeService;
   @Autowired
   private UserService userService;


   /**
    * Simply selects the home view to render by returning its name.
    */
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String home(Locale locale, Model model, Criteria cri) {
      logger.info("Welcome home! The client locale is {}.", locale);
      logger.info("YO! Welcome home! The client locale is {}.", locale);
      Date date = new Date();
      DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
      String formattedDate = dateFormat.format(date);
      RecipeVO latestRecipe = recipeService.getLatestRecipe();
      UserVO author = new UserVO();
      if(latestRecipe!=null) {
    	  author = userService.getUserByNo(latestRecipe.getUserNo());
      }
      model.addAttribute("latestRecipe", latestRecipe);
      model.addAttribute("author", author);
//      model.addAttribute("tag",recipeService.getTagNameList());
//      model.addAttribute("pageMaker", new PageDTO(cri, 100));
//      model.addAttribute("list", recipeService.getList());
      model.addAttribute("list", recipeService.getList().subList(0, 12));

      return "index";
   }
   
   @GetMapping("/cookInfo")
   public void cookInfo() {
      
   }
   
   @GetMapping("/good")
   public void getGood() {
      
   }

	@PostMapping("/good")
	public void postGood() {
		
	}
	
	@GetMapping("/error")
	public void getError() {
		
	}
}

