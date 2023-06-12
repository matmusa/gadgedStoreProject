package peaksoft.dto.request;

public record UserRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
