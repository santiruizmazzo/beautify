package beautify

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class AppointmentSpec extends Specification implements DomainUnitTest<Appointment> {

    def setup() {
    }

    def cleanup() {
    }

    void "Rating an appointment with a score outside the range from 0 to 5, should fail"() {
        
    }

    void "Rating an appointment without attending it previously, should fail"() {
        
    }

    void "Rating an appointment when its rating time is over, should fail"() {

    }
}
