package beautify

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import beauty_category.BeautyCategory
import time_range.TimeRange
import java.time.*

class CustomerSpec extends Specification implements DomainUnitTest<Customer> {

    void "scheduling appointment with an empty appointments list"() {
        given:
            Customer customer = new Customer("Mario", "Rodriguez", "mariorod@gmail.com", "4321")
            TimeRange timeRange = new TimeRange(LocalDateTime.of(2023, 1, 1, 5, 0), LocalDateTime.of(2023, 1, 1, 5, 30))
            Appointment appointment = new Appointment(new BigDecimal(100), timeRange)
        
        when:
            customer.schedule(appointment)

        then:
            customer.appointments.size() == 1
    }
}
