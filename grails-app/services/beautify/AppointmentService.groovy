package beautify

import grails.gorm.transactions.Transactional
import java.time.*
import time_range.TimeRange

@Transactional
class AppointmentService {
    
    def appointmentRepository

    Appointment make(Long customerId, Long beautyServiceId, LocalDateTime startDateTime) {
        Customer customer = Customer.get(customerId)
        BeautyService beautyService = BeautyService.get(beautyServiceId)
        TimeRange timeRange = new TimeRange(startDateTime, startDateTime.plusMinutes(beautyService.duration.longValue()))
        Appointment appointment = beautyService.makeAppointment(customer, timeRange, appointmentRepository)
        appointment.save()
    }

    Appointment rate(Long appointmentId, Integer rating, String comment) {
        Appointment appointment = Appointment.get(appointmentId)
        appointment.rate(rating, comment)
        appointment.save()
    }

    def cancel(Long appointmentId) {
        Appointment appointment = Appointment.get(appointmentId)
        if (!appointment.isWithinCancellationTime()) {
            throw new RuntimeException("El tiempo para cancelar el turno ya termino")
        }
        appointment.delete()
    }
}
