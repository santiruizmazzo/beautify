package beautify

class Customer {

    String name
    String surname
    String email
    String password

    static hasMany = [appointments: Appointment]

    static constraints = {
        name blank: false
        surname blank: false
        email email: true, blank: false, unique: true
        password blank: false, unique: true
    }

    Customer(String name, String surname, String email, String password) {
        this.name = name
        this.surname = surname
        this.email = email
        this.password = password
    }

    static class AppointmentOverlappingException extends RuntimeException {
        AppointmentOverlappingException(String errorMessage) {
            super(errorMessage)
        }
    }

    Boolean existsOverlappingFor(Appointment newAppointment) {
        appointments ? (appointments.findAll{ !it.attended }.any{ it.timeRange.overlaps(newAppointment.timeRange) }) : false
    }

    void schedule(Appointment newAppointment) {
        if (existsOverlappingFor(newAppointment)) {
            throw new AppointmentOverlappingException("El turno seleccionado se solapa con uno que ya reservaste")
        }
        addToAppointments(newAppointment)
    }
}
