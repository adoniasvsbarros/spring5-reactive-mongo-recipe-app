package guru.springframework.services;

import org.springframework.stereotype.Service;

import guru.springframework.commands.RecipeCommand;
import guru.springframework.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface RecipeService {
	
	Flux<Recipe> getRecipes();
	
	Mono<Recipe> findById(String id);
	
	Mono<RecipeCommand> findCommandById(String id);
	
	Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command);
	
	void deleteById(String id);
}
