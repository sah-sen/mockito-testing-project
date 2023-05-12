package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;


public class Test08Spies {

    private BookingService bookingService;
    private PaymentService paymentServiceMock;
    private RoomService roomServiceMock;
    private BookingDAO bookingDAOMock;
    private MailSender mailSenderMock;

    @BeforeEach
    void setup(){
        this.paymentServiceMock = mock(PaymentService.class);
        this.roomServiceMock = mock(RoomService.class);
        this.bookingDAOMock = spy(BookingDAO.class);
        //mocks are dummy objects wiht no real logic
        //spy is a real object with real logic that can be modified
        this.mailSenderMock = mock(MailSender.class);

        this.bookingService = new BookingService(paymentServiceMock, roomServiceMock,
                bookingDAOMock, mailSenderMock);
    }

    @Test
    @DisplayName("Should make booking when input is ok")
    public void shouldMakeBookingWhenInputIsOk() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020,01,01),
                LocalDate.of(2020,01,05),2,false);
        //when

        String bookingID = bookingService.makeBooking(bookingRequest);

        //then
        verify(bookingDAOMock).save(bookingRequest);
        System.out.println("bookingId =" + bookingID);
        }



    @Test
    @DisplayName("Should cancel booking when input is ok")
    public void shouldCancelBookingWhenInputIsOk() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020,01,01),
                LocalDate.of(2020,01,05),2,true);

        bookingRequest.setRoomId("1.3");
        String bookingId ="1";

        doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);

        //when
        bookingService.cancelBooking(bookingId);

        //then
    }



}
