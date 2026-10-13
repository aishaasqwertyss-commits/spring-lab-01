package kz.iitu.springlab.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.URI;
import java.util.Locale;
import java.util.Set;

public class CoverImageUrlValidator
        implements ConstraintValidator<CoverImageUrl, String> {

    private static final Set<String> ALLOWED_EXTENSIONS =
            Set.of(".jpg", ".jpeg", ".png", ".webp");

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return true;
        }

        try {
            URI uri = URI.create(value.trim());

            String scheme = uri.getScheme();
            String host = uri.getHost();
            String path = uri.getPath();

            if (scheme == null || host == null || path == null) {
                return false;
            }

            if (!scheme.equalsIgnoreCase("http")
                    && !scheme.equalsIgnoreCase("https")) {
                return false;
            }

            String lowerPath = path.toLowerCase(Locale.ROOT);

            return ALLOWED_EXTENSIONS.stream()
                    .anyMatch(lowerPath::endsWith);

        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}