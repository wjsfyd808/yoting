package org.kitchen.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.kitchen.domain.Criteria;
import org.kitchen.domain.PageDTO;
import org.kitchen.service.RecipeService;
import org.kitchen.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
   public String home(Locale locale, Model model) {
      logger.info("Welcome home! The client locale is {}.", locale);
      logger.info("YO! Welcome home! The client locale is {}.", locale);

      Date date = new Date();
      DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

      String formattedDate = dateFormat.format(date);
      
      int size = recipeService.getList().size();
      Long rno = new Long(size);
      Long user = recipeService.isMyRecipe(rno);

      model.addAttribute("serverTime", formattedDate);
      model.addAttribute("list", recipeService.getList());
      model.addAttribute("last", recipeService.get(rno));
      model.addAttribute("lastUser", userService.getUserByNo(user));
      
      return "index";
   }
   
   @GetMapping("/index")
   public void search(Criteria cri, Model model) {
	  model.addAttribute("tag",recipeService.getTagNameList());
      model.addAttribute("pageMaker", new PageDTO(cri, 100));
      model.addAttribute("list", recipeService.getList());
   }

   @GetMapping("/good")
   public void good(String result, Model model) {
      model.addAttribute("result", result);
   }

   @GetMapping("/error")
   public void error(String result, Model model) {
      model.addAttribute("result", result);
   }

}


