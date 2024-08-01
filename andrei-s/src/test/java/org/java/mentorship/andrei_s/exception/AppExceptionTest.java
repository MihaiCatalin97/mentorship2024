package org.java.mentorship.andrei_s.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.*;

class AppExceptionTest {

    @Test
    void constructorShouldSetFields() {
        RuntimeException exception = new RuntimeException("Original Exception");
        AppException appException = new AppException("Hello", exception);

        assertThat(appException.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(appException.getOriginalException()).isEqualTo(exception);
    }
}