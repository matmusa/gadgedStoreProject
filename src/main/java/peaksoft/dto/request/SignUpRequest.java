package peaksoft.dto.request;

import lombok.Builder;
import peaksoft.enums.Role;
@Builder
public record SignUpRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        Role role

) {
}
