package beautify

import java.time.*
import java.time.format.TextStyle
import beauty_category.BeautyCategory
import time_range.TimeRange

class BeautyService {

    String name
    String description
    BeautyCategory category
    BigDecimal price
    Integer duration

    static hasMany = [appointments: Appointment]

    static constraints = {
        name blank: false
        description blank: true
        price min: new BigDecimal(0)
        duration min: 0
    }

    BeautyService(String name, String description, BeautyCategory category, BigDecimal price, Integer duration) {
        this.name = name
        this.description = description
        this.category = category
        this.price = price
        this.duration = duration
    }

    static class IncompatibleAppointmentStartTimeException extends RuntimeException {
        IncompatibleAppointmentStartTimeException(String errorMessage) {
            super(errorMessage)
        }
    }

    static class AppointmentAlreadyTakenException extends RuntimeException {
        AppointmentAlreadyTakenException(String errorMessage) {
            super(errorMessage)
        }
    }

    Boolean matchesWithSchedule(TimeRange timeRange) {
        LocalTime currentTime = LocalTime.of(8,0)
        def timeSchedule = [currentTime]

        while (currentTime.isBefore(LocalTime.of(21,0))) {
            currentTime = currentTime.plusMinutes(duration)
            timeSchedule.add(currentTime)
        }

        timeRange.start.toLocalTime() in timeSchedule
    }

    Boolean isWorkableDay(TimeRange timeRange) {
        !(timeRange.start.getDayOfWeek() in [DayOfWeek.SUNDAY])
    }

    Boolean isAlreadyTaken(TimeRange timeRange) {
        appointments ? (appointments.any{ it.timeRange.equals(timeRange) && !it.cancelled }) : false
    }

    Appointment makeAppointmentFor(TimeRange timeRange) {
        if (!matchesWithSchedule(timeRange)) {
            throw new IncompatibleAppointmentStartTimeException("No se ofrece ningun turno a las ${timeRange.start.toLocalTime()}")
        } else if (!isWorkableDay(timeRange)) {
            String dayOfWeekInSpanish = timeRange.start.getDayOfWeek().getDisplayName(TextStyle.FULL, new Locale("es", "ES"))
            throw new IncompatibleAppointmentStartTimeException("No se ofrecen turnos los dias ${dayOfWeekInSpanish}s")
        } else if (isAlreadyTaken(timeRange)) {
            throw new AppointmentAlreadyTakenException("El turno ya fue reservado por otro cliente")
        }

        Appointment newAppointment = new Appointment(price, timeRange)
        addToAppointments(newAppointment)
        newAppointment
    }
}
