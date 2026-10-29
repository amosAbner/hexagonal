package com.study.hexagonal.application.core.usecase;

import com.study.hexagonal.application.core.exception.CustomerNotFoundException;
import com.study.hexagonal.application.ports.out.FindCustomerByIdOutputPort;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindCustomerUseCaseTest {

    @Test
    void shouldThrowCoreExceptionWhenCustomerDoesNotExist() {
        var outputPort = mock(FindCustomerByIdOutputPort.class);
        when(outputPort.find("customer-123")).thenReturn(Optional.empty());
        var useCase = new FindCustomerUseCase(outputPort);

        assertThrows(CustomerNotFoundException.class, () -> useCase.find("customer-123"));
    }
}
