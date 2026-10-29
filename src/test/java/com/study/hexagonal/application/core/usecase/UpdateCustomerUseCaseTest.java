package com.study.hexagonal.application.core.usecase;

import com.study.hexagonal.application.core.domain.Address;
import com.study.hexagonal.application.core.domain.Customer;
import com.study.hexagonal.application.ports.in.FindCustomerInputPort;
import com.study.hexagonal.application.ports.out.FindAddressByZipCodeOutputPort;
import com.study.hexagonal.application.ports.out.SendCpfForValidationOutpuPort;
import com.study.hexagonal.application.ports.out.UpdateCustomerOutputPort;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateCustomerUseCaseTest {

    private final FindCustomerInputPort findCustomer = mock(FindCustomerInputPort.class);
    private final FindAddressByZipCodeOutputPort findAddress = mock(FindAddressByZipCodeOutputPort.class);
    private final UpdateCustomerOutputPort updateCustomer = mock(UpdateCustomerOutputPort.class);
    private final SendCpfForValidationOutpuPort sendCpfValidation = mock(SendCpfForValidationOutpuPort.class);
    private final UpdateCustomerUseCase useCase =
            new UpdateCustomerUseCase(findCustomer, findAddress, updateCustomer, sendCpfValidation);

    @Test
    void shouldUpdateCustomerAndRequestCpfValidationWhenCpfChanges() {
        var existing = new Customer("customer-id", "Old Name", "11111111111", true, null);
        var requested = new Customer(null, "New Name", "22222222222", false, null);
        var address = new Address("New Street", "New City", "SP");
        when(findCustomer.find("customer-id")).thenReturn(existing);
        when(findAddress.find("01001000")).thenReturn(address);

        useCase.update(withId(requested, "customer-id"), "01001000");

        assertEquals("New Name", existing.getName());
        assertEquals("22222222222", existing.getCpf());
        assertEquals(address, existing.getAddress());
        assertFalse(existing.getIsValidCpf());
        verify(updateCustomer).update(existing, "01001000");
        verify(sendCpfValidation).send("22222222222");
    }

    @Test
    void shouldPreserveCpfValidationWhenCpfDoesNotChange() {
        var existing = new Customer("customer-id", "Old Name", "11111111111", true, null);
        var requested = withId(new Customer(null, "New Name", "11111111111", false, null), "customer-id");
        when(findCustomer.find("customer-id")).thenReturn(existing);
        when(findAddress.find("01001000")).thenReturn(new Address("Street", "City", "SP"));

        useCase.update(requested, "01001000");

        assertEquals("New Name", existing.getName());
        assertTrue(existing.getIsValidCpf());
        verify(sendCpfValidation, never()).send(any());
        verify(updateCustomer).update(existing, "01001000");
    }

    private Customer withId(Customer customer, String id) {
        customer.setId(id);
        return customer;
    }
}
