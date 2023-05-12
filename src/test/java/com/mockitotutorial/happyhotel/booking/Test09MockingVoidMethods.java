package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class Test09MockingVoidMethods {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup() {
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock,
                bookingDAOMock, mailSenderMock);
    }

    @Test
    @DisplayName("should throw exception when mail not ready 1st")
    public void shouldThrowExceptionWHenMailnotReady1() {
        //given
        BookingRequest bookingRequestFail = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, false);

        doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());
        //when
        Executable executable = () -> bookingService.makeBooking(bookingRequestFail);

        //then
        assertThrows(BusinessException.class, executable);
    }

    @Test
    @DisplayName("Should not throw exception when mail not ready")
    public void shouldNotThrowExceptionWhenMailNotReady() {

        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 01, 01),
                LocalDate.of(2020, 01, 05), 2, false);

        doNothing().when(mailSenderMock).sendBookingConfirmation(any());
        //when
        bookingService.makeBooking(bookingRequest);

        //then
        // expect no excpetion thrown

    }
}
