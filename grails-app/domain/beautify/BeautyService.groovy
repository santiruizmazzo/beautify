package beautify
import java.time.*
import beauty_category.*

class BeautyService {

    String name
    String description
    BeautyCategory category
    BigDecimal price
    LocalTime timeToCancelBeforeStart
    AppointmentSchedule schedule

    static constraints = {
        name blank: false, unique: true
        description blank: true
        price min: new BigDecimal(0)
    }

    static class IncompatibleAppointmentTimeDetailException extends Exception {
        IncompatibleAppointmentTimeDetailException(String errorMessage) {
            super(errorMessage)
        }
    }

    Appointment bookAppointmentFor(Customer customer, TimeDetail timeDetail) {
        if (this.offDays.contains(timeDetail.dayOfWeek()) || this.duration != timeDetail.duration() || !timeDetail.isWithin(this.workingHours)) {
            throw new IncompatibleAppointmentTimeDetailException("Los horarios del turno no son compatibles con los del servicio")
        }

        new Appointment(timeDetail: timeDetail, servicePriceWhenBooked: this.price, attendedByCustomer: false, customer: customer, beautyService, this)
    }
}
