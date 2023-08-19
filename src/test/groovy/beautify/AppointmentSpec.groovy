package beautify

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import time_range.TimeRange
import beauty_category.BeautyCategory
import java.time.*

class AppointmentSpec extends Specification implements DomainUnitTest<Appointment> {

    void "giving appointment a rating greater than 5, should fail"() {
        given:
            TimeRange timeRange = new TimeRange(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30L))
            Appointment appointment = new Appointment(new BigDecimal(100), timeRange)
            appointment.attended = true
        
        when:
            appointment.rate(7, "comentario")

        then:
            thrown(Appointment.InvalidRatingException)
            appointment.rating == null && appointment.comment == null
    }

    void "giving appointment a rating less than 0, should fail"() {
        given:
            TimeRange timeRange = new TimeRange(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30L))
            Appointment appointment = new Appointment(new BigDecimal(100), timeRange)
            appointment.attended = true
        
        when:
            appointment.rate(-2, "comentario")

        then:
            thrown(Appointment.InvalidRatingException)
            appointment.rating == null && appointment.comment == null
    }

    void "rating appointment without attending it previously, should fail"() {
        given:
            TimeRange timeRange = new TimeRange(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30L))
            Appointment appointment = new Appointment(new BigDecimal(100), timeRange)
        
        when:
            appointment.rate(3, "comentario")

        then:
            thrown(Appointment.RatingWithoutAttendanceException)
            appointment.rating == null && appointment.comment == null
    }

    void "rating appointment when its rating time is over, should fail"() {
        given:
            TimeRange timeRange = new TimeRange(LocalDateTime.of(2023,1,1,8,0), LocalDateTime.of(2023,1,1,8,30))
            Appointment appointment = new Appointment(new BigDecimal(100), timeRange)
            appointment.attended = true
        
        when:
            appointment.rate(3, "comentario")

        then:
            thrown(Appointment.EndOfRatingTimeException)
            appointment.rating == null && appointment.comment == null
    }

    void "rating already rated appointment, should fail"() {
        given:
            TimeRange timeRange = new TimeRange(LocalDateTime.now(), LocalDateTime.now().plusMinutes(30L))
            Appointment appointment = new Appointment(new BigDecimal(100), timeRange)
            appointment.attended = true
        
        when:
            appointment.rate(3, "comentario")
            appointment.rate(5, "un segundo comentario")

        then:
            thrown(Appointment.AlreadyRatedAppointmentException)
            appointment.rating == 3 && appointment.comment == "comentario"
    }

    void "cancelling appointment within its cancellation time"() {
        given:
            TimeRange timeRange = new TimeRange(LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(4))
            Appointment appointment = new Appointment(new BigDecimal(100), timeRange)
        
        when:
            appointment.cancel()

        then:
            appointment.cancelled == true
    }

    void "cancelling appointment when its cancellation time is over"() {
        given:
            TimeRange timeRange = new TimeRange(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2))
            Appointment appointment = new Appointment(new BigDecimal(100), timeRange)
        
        when:
            appointment.cancel()

        then:
            thrown(Appointment.EndOfCancellationTimeException)
            appointment.cancelled == false
    }
}
