package com.study.hexagonal.adapters.out;

import com.study.hexagonal.adapters.out.client.FindAddressByZipCodeClient;
import com.study.hexagonal.adapters.out.client.response.AddressResponse;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindAddressByZipCodeAdapterTests {

    private final FindAddressByZipCodeClient client = mock(FindAddressByZipCodeClient.class);
    private final FindAddressByZipCodeAdapter adapter = new FindAddressByZipCodeAdapter(client);

    @Test
    void mapsAddressAndNormalizesZipCode() {
        when(client.findAddressByZipCode("01001000"))
                .thenReturn(new AddressResponse("Praca da Se", "Sao Paulo", "SP", null));

        var address = adapter.find("01001-000");

        assertEquals("Praca da Se", address.getStreet());
        assertEquals("Sao Paulo", address.getCity());
        assertEquals("SP", address.getState());
        verify(client).findAddressByZipCode("01001000");
    }

    @Test
    void rejectsUnknownZipCode() {
        when(client.findAddressByZipCode("00000000"))
                .thenReturn(new AddressResponse(null, null, null, true));

        assertThrows(NoSuchElementException.class, () -> adapter.find("00000000"));
    }

    @Test
    void rejectsInvalidZipCodeBeforeCallingClient() {
        for (String zipCode : new String[]{null, "", "123", "abcdefgh", "01001--000"}) {
            assertThrows(IllegalArgumentException.class, () -> adapter.find(zipCode));
        }
        verifyNoInteractions(client);
    }

    @Test
    void rejectsEmptyResponse() {
        assertThrows(IllegalStateException.class, () -> adapter.find("01001000"));
    }
}
