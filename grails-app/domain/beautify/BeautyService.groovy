package beautify

import java.time.*
import beauty_category.*

interface CheckAppointmentOverlapping {
    Boolean existsOverlappingFor(Customer customer, BeautyService beautyService, TimeDetail timeDetail)
}

class BeautyService {

    String name
    String description
    BeautyCategory category
    BigDecimal price
    Integer minutesBeforeStartToCancel
    AppointmentSchedule schedule

    static constraints = {
        name blank: false, unique: true
        description blank: true
        price min: new BigDecimal(0)
        minutesBeforeStartToCancel min: 0
    }

    static class IncompatibleAppointmentTimeDetailException extends RuntimeException {
        IncompatibleAppointmentTimeDetailException(String errorMessage) {
            super(errorMessage)
        }
    }

    static class ExistingAppointmentOverlappingException extends RuntimeException {
        ExistingAppointmentOverlappingException(String errorMessage) {
            super(errorMessage)
        }
    }

    Appointment book(Customer customer, TimeDetail timeDetail, CheckAppointmentOverlapping overlappingChecker) {
        if (!schedule.matchesWith(timeDetail)) {
            throw new IncompatibleAppointmentTimeDetailException("Los horarios del turno no son compatibles con los del servicio")
        } else if (overlappingChecker.existsOverlappingFor(customer, this, timeDetail)) {
            throw new ExistingAppointmentOverlappingException("El turno a reservar se solapa con uno ya reservado por el cliente")
        }

        // falta chequear que el turno no haya sido reservado por otro cliente (ya pregunte a Mauro)

        new Appointment(timeDetail: timeDetail, servicePriceWhenBooked: price, attendedByCustomer: false, customer: customer, beautyService, this)
    }
}
