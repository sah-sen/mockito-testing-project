
package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class Test10ArgumentCaptors {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;
    private ArgumentCaptor<Double> doubleCaptor;


    @BeforeEach
    void setup(){
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = mock(BookingDAO.class);
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock,
                bookingDAOMock, mailSenderMock);

        this.doubleCaptor =ArgumentCaptor.forClass(Double.class);

    }
    @Test
    @DisplayName("Should pay correct price when input ok")
    public void shouldPayCorrectPriceWhenInputOK() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020,01,01),
                LocalDate.of(2020,01,05),2,true);
      //when

        bookingService.makeBooking(bookingRequest);

        //then
        verify(paymentServiceMock, times(1)).pay(eq(bookingRequest), doubleCaptor.capture());
        double capturedArgument = doubleCaptor.getValue();

        assertEquals(400.0, capturedArgument);
    }


    @Test
    @DisplayName("Should pay correct prices when multiple calls")
    public void shouldPayCorrectPricesWhenMultipleCalls() {

        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020,01,01),
                LocalDate.of(2020,01,05),2,true);
        BookingRequest bookingRequest2 = new BookingRequest("1", LocalDate.of(2020,01,01),
                LocalDate.of(2020,01,02),2,true);

        List<Double> expectedValues = Arrays.asList(400.0,100.0);
        //when

        bookingService.makeBooking(bookingRequest);
        bookingService.makeBooking(bookingRequest2);

        //then
        verify(paymentServiceMock, times(2)).pay(any(), doubleCaptor.capture());
        List<Double> capturedArguments = doubleCaptor.getAllValues();

        assertEquals(expectedValues, capturedArguments);        }
}
