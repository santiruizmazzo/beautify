package beautify

class Customer {

    String name
    String surname
    String email
    Long phone

    static hasMany = [appointments : Appointment]

    static constraints = {
        name blank: false
        surname blank: false
        email email: true, blank: false, unique: true
        phone unique: true, min: 0L
    }
}
