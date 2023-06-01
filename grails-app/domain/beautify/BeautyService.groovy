package beautify
import java.time.*
import beauty_categories.*

class BeautyService {

    String commercialName
    String description
    BeautyCategories category
    BigDecimal currentPrice
    List<DayOfWeek> offDays
    TimeRange workingHours
    Duration duration

    static constraints = {
        commercialName blank: false, unique: true
        description blank: true
        currentPrice min: new BigDecimal(0)
        offDays size: 0..6
    }

    static class IncompatibleAppointmentTimeDetailException extends Exception {
        IncompatibleAppointmentTimeDetailException(String errorMessage) {
            super(errorMessage)
        }
    }

    Appointment bookAppointmentFor(Customer customer, AppointmentTimeDetail timeDetail) {
        if (this.offDays.contains(timeDetail.dayOfWeek()) || this.duration != timeDetail.duration() || !timeDetail.isWithin(this.workingHours)) {
            throw new IncompatibleAppointmentTimeDetailException("Los horarios del turno no son compatibles con los del servicio")
        }

        new Appointment(  timeDetail: timeDetail, servicePriceWhenBooked: this.currentPrice, attendedByCustomer: false, customer: customer, beautyService, this)
    }
}
