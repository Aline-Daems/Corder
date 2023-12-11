package be.technobel.corder.pl.models.forms;

public record SatisfactionForm(
        Long id,
        int satisfaction,
        String satisfactionComment
) {
}
