package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;


public class Test07VerifyingBehaviour {

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
    @DisplayName("Should invoke payment when prepaid")
    public void shouldInvokePaymentWhenPrepaid() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020,01,01),
                LocalDate.of(2020,01,05),2,false);
      //when

        bookingService.makeBooking(bookingRequest);

        //then


        //checks that the pay method contains the following paramaters
//        verify(paymentServiceMock).pay(bookingRequest,400.0);
    //    verifyNoMoreInteractions(paymentServiceMock);
        //this makes sure the above method was only used once, passes only if prepaid is true


        verify(paymentServiceMock, never()).pay(any(),anyDouble());
        //verify that works when not prepaid (false)
    }


}
