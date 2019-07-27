package guru.springframework.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.exceptions.NotFoundException;
import guru.springframework.services.RecipeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {

	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
	private final RecipeService recipeService;
	
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping("recipe/{id}/show")
	public String showById(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findById(id).block());
		
		return "recipe/show";
	}
	
	@GetMapping("recipe/new")
	public String newRecipe (Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		return RECIPE_RECIPEFORM_URL;
	}
	
	//@RequestMapping(name = "recipe", method = RequestMethod.POST)
	@PostMapping("recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult ) {
		
		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> {
				log.error(objectError.toString());
			});
			
			return RECIPE_RECIPEFORM_URL;
		}
		
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command).block();
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
	
	@GetMapping("recipe/{id}/update")
	public String updateRecipe(@PathVariable String id,  Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(id).block());
		return "recipe/recipeform";
	}
	
	@GetMapping("recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id, Model model) {
		log.debug("Deleting recipe id:" + id);
		
		recipeService.deleteById(id);
		return "redirect:/";
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		log.error("Handling not found exception");
		log.error(exception.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404error");
		modelAndView.addObject("exception", exception);
		
		
		return modelAndView;
	}
}