package beautify

class Customer {

    String name
    String surname
    String email
    String password

    static constraints = {
        name blank: false
        surname blank: false
        email email: true, blank: false, unique: true
        password blank: false, unique: true
    }
}
