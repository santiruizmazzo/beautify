package beautify

import java.time.*
import java.time.temporal.ChronoUnit
import time_range.TimeRange

class Appointment {

    Customer customer
    BeautyService beautyService
    BigDecimal servicePriceWhenBooked
    TimeRange timeRange
    Boolean attended
    Integer rating
    String comment

    static final Integer MIN_RATING = 0
    static final Integer MAX_RATING = 5
    static final Integer MAX_RATING_HOURS = 24
    static final Integer MINS_BEFORE_START_TO_CANCEL = 120

    static embedded = ['timeRange']

    static constraints = {
        servicePriceWhenBooked min: new BigDecimal(0)
        rating nullable: true, min: 0, max: 5
        comment nullable: true
    }

    Appointment(Customer customer, BeautyService beautyService, TimeRange timeRange) {
        this.customer = customer
        this.beautyService = beautyService
        servicePriceWhenBooked = beautyService.price
        this.timeRange = timeRange
        attended = false
    }

    static class AlreadyRatedAppointmentException extends RuntimeException {
        AlreadyRatedAppointmentException(String errorMessage) {
            super(errorMessage)
        }
    }

    static class RatingWithoutAttendanceException extends RuntimeException {
        RatingWithoutAttendanceException(String errorMessage) {
            super(errorMessage)
        }
    }

    static class InvalidRatingException extends RuntimeException {
        InvalidRatingException(String errorMessage) {
            super(errorMessage)
        }
    }

    static class EndOfRatingTimeException extends RuntimeException {
        EndOfRatingTimeException(String errorMessage) {
            super(errorMessage)
        }
    }

    Boolean isValidRating(Integer ratingToValidate) {
        MIN_RATING <= ratingToValidate && ratingToValidate <= MAX_RATING
    }

    Boolean isWithinRatingTime() {
        timeRange.end.until(LocalDateTime.now(), ChronoUnit.HOURS) <= MAX_RATING_HOURS
    }

    def rate(Integer rating, String comment) {
        if (isRated()) {
            throw new AlreadyRatedAppointmentException("No se puede calificar un turno mas de una vez")
        } else if (!attended) {
            throw new RatingWithoutAttendanceException("No se puede calificar un turno sin asistir previamente")
        } else if (!isValidRating(rating)) {
            throw new InvalidRatingException("La calificacion del turno debe ser un numero entre 0 y 5 (ambos incluidos)")
        } else if (!isWithinRatingTime()) {
            throw new EndOfRatingTimeException("Ya pasaron mas de ${MAX_RATING_HOURS} desde que asististe al turno")
        }

        setRating rating
        setComment comment
    }

    Boolean isRated() {
        rating == null ? false : true
    }

    Boolean canStillAttend() {
        !attended && LocalDateTime.now().isBefore(timeRange.end)
    }

    Boolean isWithinCancellationTime() {
        LocalDateTime.now().until(timeRange.start, ChronoUnit.MINUTES) > MINS_BEFORE_START_TO_CANCEL
    }
}
