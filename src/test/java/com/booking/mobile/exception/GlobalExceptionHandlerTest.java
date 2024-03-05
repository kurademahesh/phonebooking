package com.booking.mobile.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.booking.mobile.dto.ApplicationResponse;
import com.booking.mobile.dto.ErrorRespose;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

	@Mock
	private HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException;

	@InjectMocks
	private GlobalExceptionHandler exceptionHandler;
	

	@Mock
	NoResourceFoundException noResourceFoundException;

	@Test
	void handleNotFoundExceptionTest() {
		// Call the method under test
		ResponseEntity<ApplicationResponse<ErrorRespose>> responseEntity = exceptionHandler
				.handleNotFoundException(httpRequestMethodNotSupportedException);

		// Assertions
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("1001", responseEntity.getBody().getData().getErrorCode());
		assertEquals("Invalid HTTP Action.", responseEntity.getBody().getData().getMessage());
	}
	
	@Test
    void handleNoResourceFoundExceptionTest() {

        // Call the method under test
        ResponseEntity<ApplicationResponse<ErrorRespose>> responseEntity = exceptionHandler.handleNoResourceFoundException(noResourceFoundException);

        // Assertions
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("1002", responseEntity.getBody().getData().getErrorCode());
        assertEquals("Invalid URL.", responseEntity.getBody().getData().getMessage());
    }
	
	
	@Test
    void handlePhoneServiceExceptionTest() {
        // Mock PhoneServiceException
        PhoneServiceException phoneServiceException = new PhoneServiceException("Phone is already reserved.");

        // Call the method under test
        ResponseEntity<ApplicationResponse<ErrorRespose>> responseEntity = exceptionHandler.handlePhoneReservedException(phoneServiceException);

        // Assertions
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("1003", responseEntity.getBody().getData().getErrorCode());
        assertEquals("Phone is already reserved.", responseEntity.getBody().getData().getMessage());
    }

    @Test
    void handleGenericExceptionTest() {
        // Mock any generic exception
        Exception exception = mock(Exception.class);
        when(exception.getMessage()).thenReturn("Some generic error.");

        // Call the method under test
        ResponseEntity<ErrorRespose> responseEntity = exceptionHandler.handleGenericException(exception);

        // Assertions
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("9999", responseEntity.getBody().getErrorCode());
        assertEquals("Internal Server Error. Please try again.", responseEntity.getBody().getMessage());
    }
}
