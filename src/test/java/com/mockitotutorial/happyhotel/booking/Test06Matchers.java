package com.mockitotutorial.happyhotel.booking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    @DisplayName("should throw exception when room is not available")
    public void shouldThrowExceptionRoomIsNotAvailable() {
        //given
        BookingRequest bookingRequestFail = new BookingRequest("1", LocalDate.of(2020,01,01),
                LocalDate.of(2020,01,005),2,false);

        when(this.roomServiceMock.findAvailableRoomId(bookingRequestFail))
                .thenThrow(BusinessException.class);

        //when
        Executable executable = () -> bookingService.makeBooking(bookingRequestFail);

        //then
        assertThrows(BusinessException.class,executable);
    }


}
