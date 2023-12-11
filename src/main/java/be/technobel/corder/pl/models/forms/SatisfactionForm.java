package be.technobel.corder.pl.models.forms;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SatisfactionForm(
        @NotNull(message = "Id cannot be null") Long id,

        @Min(value = 0, message = "Satisfaction should not be less than 0")
        @Max(value = 10, message = "Satisfaction should not be greater than 10") int satisfaction,

        @NotBlank(message = "Satisfaction comment cannot be blank") String satisfactionComment
) {
}
