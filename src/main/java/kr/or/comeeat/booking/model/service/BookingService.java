package kr.or.comeeat.booking.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.comeeat.booking.model.dao.BookingDao;
import kr.or.comeeat.booking.model.vo.Booking;

@Service
public class BookingService {
	@Autowired
	private BookingDao bookingDao;
	
	@Transactional
	public int insertBooking(Booking b) {
		int result = bookingDao.insertBooking(b);
		return result;
	}

	

	public List allBookingTime(String bookingDate, String loTitle) {
		List list = bookingDao.allBookingTime(bookingDate, loTitle);
		System.out.println(loTitle);
		return list;
	}



	public List selectAllBooking() {
		List Booking = bookingDao.selectAllBooking();
		return Booking;
	}
}
