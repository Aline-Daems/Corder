package be.technobel.corder.pl.models.forms;

import be.technobel.corder.dal.models.Participation;

public record SatisfactionForm(
        Long id,
        int satisfaction,
        String satisfactionComment
) {
}
