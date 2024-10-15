package beautify

import java.time.*

class AppointmentController {

    def appointmentService
    def messageSource

    def list() {
        Customer customer = Customer.get(session.customerId)
        [appointments: Appointment.findAllByCustomer(customer)]
    }

    def make(AppointmentInfo info) {
        if (!info.validate()) {
            flash["error"] = info.errors.allErrors.collect { messageSource.getMessage(it, Locale.getDefault()) }
            redirect controller: "beautyService", action: "detail", id: info.beautyServiceId
            return
        }

        try {
            Appointment a = appointmentService.make(session.customerId, info.beautyServiceId, info.startDateTime())
            redirect action: "madeSuccessfully", model: [appointment: a]
        } catch (Exception e) {
            flash["error"] = [e.message]
            redirect controller: "beautyService", action: "detail", id: info.beautyServiceId
        }
    }

    def madeSuccessfully() {
    }

    def detail(Long id) {
        Appointment a = Appointment.get(id)
        if (!a) {
            redirect uri: "404"
            return
        }
        [appointment: a]
    }

    def rate(RatingInfo info) {
        if (!info.validate()) {
            redirect action: "list", params: [customerId: 1]
            return
        }

        Appointment a = appointmentService.rate(info.appointmentId, info.rating, info.comment)
        redirect action: "ratedSuccessfully", id: a.id
    }

    def ratedSuccessfully(Long id) {
        [appointment: Appointment.get(id)]
    }

    def cancel(Long id) {
        try {
            appointmentService.cancel(id)
            redirect action: "cancelledSuccessfully"
        } catch(Exception e) {
            flash["error"] = e.message
            redirect action: "detail", id: id
        }
    }

    def cancelledSuccessfully() {
    }
}

class AppointmentInfo {

    Long beautyServiceId
    Date startDate
    Date sendingDate

    static constraints = {
        beautyServiceId min: 1L
        startDate validator: { start, info ->
            LocalDateTime startDateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
            LocalDateTime sendingDateTime = info.sendingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
            if (startDateTime.isBefore(sendingDateTime)) return ["startTimeInThePast"]
        }
    }

    LocalDateTime startDateTime() {
        startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
    }
}

class RatingInfo {

    Long appointmentId
    Integer rating
    String comment

    static constraints = {
        appointmentId min: 1L
        rating min: 0, max: 5
        comment blank: true
    }
}