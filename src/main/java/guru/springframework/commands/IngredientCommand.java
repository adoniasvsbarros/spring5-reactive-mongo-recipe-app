package guru.springframework.commands;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
	private String id;
	private String recipeId;
	
	@NotBlank
	@Size(min=3, max=255)
	private String description;
	
	@Min(1)
	@NotNull
	private BigDecimal amount;
	
	@NotNull
	private UnitOfMeasureCommand uom;
}
