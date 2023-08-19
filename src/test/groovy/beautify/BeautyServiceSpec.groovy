package beautify

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import time_range.TimeRange
import beauty_category.BeautyCategory
import java.time.*

class BeautyServiceSpec extends Specification implements DomainUnitTest<BeautyService> {
    
    void "making an appointment successfully"() {
        given:
            BeautyService beautyService = new BeautyService("Servicio X", "", BeautyCategory.SPA, new BigDecimal(100), 60)
            TimeRange timeRange = new TimeRange(LocalDateTime.of(2023,1,10,8,0), LocalDateTime.of(2023,1,10,9,0))
        
        when:
            beautyService.makeAppointmentFor(timeRange)

        then:
            !beautyService.appointments.isEmpty() && beautyService.appointments.first().timeRange == timeRange
    }

    void "appointment start time does not match with beauty service schedule"() {
        given:
            BeautyService beautyService = new BeautyService("Servicio X", "", BeautyCategory.SPA, new BigDecimal(100), 60)
            TimeRange timeRange = new TimeRange(LocalDateTime.of(2023,1,10,11,25), LocalDateTime.of(2023,1,10,12,25))
        
        when:
            beautyService.makeAppointmentFor(timeRange)

        then:
            thrown(BeautyService.IncompatibleAppointmentStartTimeException)
            beautyService.appointments == null
    }

    void "selected appointment day of the week is not workable"() {
        given:
            BeautyService beautyService = new BeautyService("Servicio X", "", BeautyCategory.SPA, new BigDecimal(100), 60)
            TimeRange timeRange = new TimeRange(LocalDateTime.of(2023,1,1,8,0), LocalDateTime.of(2023,1,1,9,0))
        
        when:
            beautyService.makeAppointmentFor(timeRange)

        then:
            thrown(BeautyService.IncompatibleAppointmentStartTimeException)
            beautyService.appointments == null
    }

    void "appointment was already made"() {
        given:
            BeautyService beautyService = new BeautyService("Servicio X", "", BeautyCategory.SPA, new BigDecimal(100), 60)
            TimeRange timeRange = new TimeRange(LocalDateTime.of(2023,1,10,8,0), LocalDateTime.of(2023,1,10,9,0))
        
        when:
            beautyService.makeAppointmentFor(timeRange)
            beautyService.makeAppointmentFor(timeRange)

        then:
            thrown(BeautyService.AppointmentAlreadyTakenException)
            beautyService.appointments.size() == 1
    }
}
