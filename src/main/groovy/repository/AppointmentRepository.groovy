package repository

import beautify.*

class AppointmentRepository implements CheckAppointmentOverlapping {

    Boolean existsOverlappingFor(Customer customer, BeautyService beautyService, TimeDetail timeDetail) {
        List<Appointment> appointments = Appointment.findAllByCustomerAndBeautyService(customer, beautyService)
        appointments ? (appointments.any { it.timeDetail.overlaps(timeDetail) }) : false
    }

}