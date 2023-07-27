package beautify

class Appointment {

    Customer customer
    BeautyService service
    BigDecimal servicePriceWhenBooked
    LocalDate date
    TimeRange timeRange
    LocalDateTime cancellationDeadline
    Boolean attended
    Integer rating
    String comment

    static constraints = {
        servicePriceWhenBooked min: new BigDecimal(0)
        rating min: 0
        timeDetail nullable: true
    }

    static class InvalidRatingException extends Exception {
        InvalidRatingException(String errorMessage) {
            super(errorMessage)
        }
    }

    def rate(Integer score, String comment) {
        if (!this.attended) {
            throw new InvalidRatingException("No se puede calificar un turno al que no asististe")
        }
        this.rating = ratingNumber
    }
}
