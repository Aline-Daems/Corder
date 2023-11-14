package be.technobel.corder.pl.models.dtos;

public record User(
        Long id,
        String firstName,
        String lastName,
        String login,
        String password,
        String email
) {
    public static User fromEntity(User)
}
