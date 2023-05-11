package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Test04MultipleThenReturnCalls {

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
    @DisplayName("count available places when one room is available")
    public void countAvailablePlacesWhenOneRoomIsAvailable() {
        //given
        //using the mock of the room service class, a singleton is reacted which includes a new room
        when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room 1", 5)));

        int expected = 5;
        //when
        int actual = bookingService.getAvailablePlaceCount();

        //then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should count available places when called multiple times")
    public void shouldCountAvailablePlacesWhenCalledMultipleTimes() {

        //given
        //using the mock of the room service class, a singleton is reacted which includes a new room
        when(this.roomServiceMock.getAvailableRooms())
                .thenReturn(Collections.singletonList(new Room("Room 1", 5)))
                .thenReturn(Collections.emptyList());


        int expectedFirstCall = 5;
        int expectedSecondCall = 0;
        //when
        int actualFirst = bookingService.getAvailablePlaceCount();
        int actualSecond = bookingService.getAvailablePlaceCount();

        //then
        assertAll(
                () -> assertEquals(expectedFirstCall, actualFirst),
                () -> assertEquals(expectedSecondCall, actualSecond)

        );


    }
}
