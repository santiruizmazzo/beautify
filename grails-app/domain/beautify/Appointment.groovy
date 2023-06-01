package beautify

class Appointment {

    AppointmentTimeDetail timeDetail
    BigDecimal servicePriceWhenBooked
    Boolean attendedByCustomer
    Integer rating

    static belongsTo = [customer : Customer, beautyService : BeautyService]

    static constraints = {
        servicePriceWhenBooked min: new BigDecimal(0)
        rating nullable: true, min: 0, max: 5
        timeDetail nullable: true
    }

    static class InvalidRatingException extends Exception {
        InvalidRatingException(String errorMessage) {
            super(errorMessage)
        }
    }

    def rate(ratingNumber) {
        if (!attendedByCustomer) {
            throw new InvalidRatingException("No se puede calificar un turno al que no asististe")
        }
        this.rating = ratingNumber
    }
}
