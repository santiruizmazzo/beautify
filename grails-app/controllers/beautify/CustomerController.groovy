package beautify

class CustomerController {

    static scaffold = Customer

    def login(CustomerInfo info) {
        if (!info.validate()) {
            redirect uri: "/"
        }
        session["customerId"] = Customer.findByEmailAndPassword(info.email, info.password)?.id
        redirect uri: "/"
    }

}

class CustomerInfo {
    
    String email
    String password

    static constraints = {
        email email: true, blank: false, unique: true
        password blank: false, unique: true
    }
}
