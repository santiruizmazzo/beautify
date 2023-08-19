package beautify

import grails.gorm.transactions.Transactional
import java.time.*
import time_range.TimeRange

@Transactional
class AppointmentService {

    Appointment make(Long customerId, Long beautyServiceId, LocalDateTime startDateTime) {
        Customer customer = Customer.get(customerId)
        BeautyService beautyService = BeautyService.get(beautyServiceId)

        TimeRange timeRange = new TimeRange(startDateTime, startDateTime.plusMinutes(beautyService.duration.longValue()))
        Appointment appointment = beautyService.makeAppointmentFor(timeRange)
        customer.schedule(appointment)
        appointment
    }

    void rate(Long appointmentId, Integer rating, String comment) {
        Appointment.get(appointmentId).rate(rating, comment)
    }

    void cancel(Long appointmentId) {
        Appointment.get(appointmentId).cancel()
    }
}
