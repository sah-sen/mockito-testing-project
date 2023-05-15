package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import static org.mockito.BDDMockito.*;

public class Test11AddingBDD {


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
    @DisplayName("count available places when one room is available")
    public void countAvailablePlacesWhenOneRoomIsAvailable() {
        //given
        //using the mock of the room service class, a singleton is reacted which includes a new room
        given(this.roomServiceMock.getAvailableRooms())
                .willReturn(Collections.singletonList(new Room("Room 1", 5)));

        int expected = 5;
        //when
        int actual = bookingService.getAvailablePlaceCount();

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Should invoke payment when prepaid")
    public void shouldInvokePaymentWhenPrepaid() {
        //given
        BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020,01,01),
                LocalDate.of(2020,01,05),2,true);
        //when

        bookingService.makeBooking(bookingRequest);

        //then
        then(paymentServiceMock).should(times(1)).pay(bookingRequest,400.0);
        verifyNoMoreInteractions(paymentServiceMock);
        //verify that works when not prepaid (false)
    }

}
