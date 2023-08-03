package beautify

import java.time.*

class Appointment {

    Customer customer
    BeautyService service
    BigDecimal servicePriceWhenBooked
    TimeDetail timeDetail
    LocalDateTime cancellationDeadline
    Boolean attended
    Integer customerScore
    String customerComment

    static final int MIN_SCORE = 0
    static final int MAX_SCORE = 5
    static final int MAX_RATING_HOURS = 24

    static constraints = {
        servicePriceWhenBooked min: new BigDecimal(0)
        customerScore nullable: true, min: 0
        comment nullable: true
    }

    static class RatingWithoutAttendanceException extends RuntimeException {
        RatingWithoutAttendanceException(String errorMessage) {
            super(errorMessage)
        }
    }

    static class InvalidRatingScoreException extends RuntimeException {
        InvalidRatingScoreException(String errorMessage) {
            super(errorMessage)
        }
    }

    static class EndOfRatingTimeException extends RuntimeException {
        EndOfRatingTimeException(String errorMessage) {
            super(errorMessage)
        }
    }

    Boolean isValidScore(Integer score) {
        MIN_SCORE <= score && score <= MAX_SCORE
    }

    Boolean isWithinRatingTime() {
        timeDetail.endDateTime().until(LocalDateTime.now(), ChronoUnit.HOURS) <= MAX_RATING_HOURS
    }

    def rate(Integer score, String comment) {
        if (!attended) {
            throw new RatingWithoutAttendanceException("No se puede calificar un turno sin asistir previamente")
        } else if (!isValidScore(score)) {
            throw new InvalidRatingScoreException("La puntuacion de un turno debe ser un numero entero entre 0 y 5")
        } else if (!isWithinRatingTime()) {
            throw new EndOfRatingTimeException("Ya pasaron mas de ${MAX_RATING_HOURS} desde que asististe al turno")
        }

        customerScore = score
        customerComment = comment
    }
}
