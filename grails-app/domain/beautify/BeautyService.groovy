package beautify

import java.time.*
import java.time.format.TextStyle
import beauty_category.BeautyCategory
import repository.CheckExistingAppointments
import time_range.TimeRange

class BeautyService {

    String name
    String description
    BeautyCategory category
    BigDecimal price
    Integer duration

    static constraints = {
        name blank: false
        description blank: true
        price min: new BigDecimal(0)
        duration min: 0
    }

    static class IncompatibleAppointmentTimeRangeException extends RuntimeException {
        IncompatibleAppointmentTimeRangeException(String errorMessage) {
            super(errorMessage)
        }
    }

    static class ExistingAppointmentOverlappingException extends RuntimeException {
        ExistingAppointmentOverlappingException(String errorMessage) {
            super(errorMessage)
        }
    }

    static class AppointmentAlreadyTakenException extends RuntimeException {
        AppointmentAlreadyTakenException(String errorMessage) {
            super(errorMessage)
        }
    }

    Boolean matchesWithAnAppointment(TimeRange timeRange) {
        LocalTime currentTime = LocalTime.of(8,0)
        def timeSchedule = [currentTime]

        while (currentTime.isBefore(LocalTime.of(21,0))) {
            currentTime = currentTime.plusMinutes(duration)
            timeSchedule.add(currentTime)
        }

        timeRange.start.toLocalTime() in timeSchedule
    }

    Boolean isNonWorkableDay(TimeRange timeRange) {
        DayOfWeek rangeDayOfWeek = timeRange.start.getDayOfWeek()
        rangeDayOfWeek in [DayOfWeek.SUNDAY]
    }

    Appointment makeAppointment(Customer customer, TimeRange timeRange, CheckExistingAppointments appointmentsChecker) {        
        if (!matchesWithAnAppointment(timeRange)) {
            throw new IncompatibleAppointmentTimeRangeException("No se ofrece ningun turno a las ${timeRange.start.toLocalTime()}")
        } else if (isNonWorkableDay(timeRange)) {
            String dayOfWeekInSpanish = timeRange.start.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
            throw new IncompatibleAppointmentTimeRangeException("No se ofrecen turnos los dias ${dayOfWeekInSpanish}s")
        } else if (appointmentsChecker.existsOverlappingFor(customer, timeRange)) {
            throw new ExistingAppointmentOverlappingException("El turno seleccionado se solapa con uno que ya reservaste")
        } else if (appointmentsChecker.isTaken(this, timeRange)) {
            throw new AppointmentAlreadyTakenException("El turno ya fue reservado por otro cliente")
        }

        new Appointment(customer, this, timeRange)
    }
}
