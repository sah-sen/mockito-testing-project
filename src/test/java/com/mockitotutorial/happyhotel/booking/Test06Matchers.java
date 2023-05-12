package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class Test06Matchers {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup(){
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock,
                bookingDAOMock, mailSenderMock);
    }


    @Test
    @DisplayName("Should not complete booking when price too high")
    public void shouldNotCompleteBookingWhenPriceTooHigh() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020,01,01),
                LocalDate.of(2020,01,005),2,true);

        //any() makes it so that any version of the object in the brackets is used, anyDouble() is for primitive types
        //instead of anyDouble, for specific values use eq() to make equals
        when(this.paymentServiceMock.pay(any(), eq(400.0))).thenThrow(BusinessException.class);

        //when
        Executable executable = () -> bookingService.makeBooking(bookingRequest);

        //then
        assertThrows(BusinessException.class,executable);
        }
/*
Matches rules,

Use any() for objects, for primitives use anyDouble(), anyBoolean() etc

use eq() to mix matchers and concrete values:  mathos(any(), eq(400.0))

for nullable strings, use any(), anyString() will not use null value
 */
}
