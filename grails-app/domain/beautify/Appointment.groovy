package beautify

class Appointment {

    BookedMoment bookedMoment
    BigDecimal priceAtBooking
    Boolean attendedByCustomer
    Integer rating

    static belongsTo = [customer : Customer, beautyService : BeautyService]

    static constraints = {
        priceAtBooking min: new BigDecimal(0)
        rating nullable: true, min: 0, max: 5
        bookedMoment nullable: true
    }
}
