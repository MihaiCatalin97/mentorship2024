package org.java.mentorship.andrei_s.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class EntityNotFoundTest {
    @Test
    void constructorShouldSetFields() {
        EntityNotFound entityNotFound = new EntityNotFound(1, "entity");

        assertThat(entityNotFound.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(entityNotFound.getMessage()).isEqualTo("Couldn't find 'entity' with ID '1'");
    }
}