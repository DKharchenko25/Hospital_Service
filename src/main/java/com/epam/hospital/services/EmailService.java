package com.epam.hospital.services;

import com.epam.hospital.data_access_layer.models.Patient;

public interface EmailService {

    void sendHospitalCard(Patient patient);
}
