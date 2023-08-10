package repository

import beautify.*
import time_range.TimeRange

interface CheckExistingAppointments {
    Boolean existsOverlappingFor(Customer customer, TimeRange timeRange)
    
    Boolean isTaken(BeautyService beautyService, TimeRange timeRange)
}

class AppointmentRepository implements CheckExistingAppointments {

    Boolean existsOverlappingFor(Customer customer, TimeRange timeRange) {
        List<Appointment> appointments = Appointment.findAllWhere(customer: customer, attended: false)
        appointments ? (appointments.any { it.timeRange.overlaps(timeRange) }) : false
    }

    Boolean isTaken(BeautyService beautyService, TimeRange timeRange) {
        Appointment.findByBeautyServiceAndTimeRange(beautyService, timeRange) ? true : false
    }
}