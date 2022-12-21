package com.epam.hospital.services;

import com.epam.hospital.models.Patient;

public interface EmailService {

    void sendHospitalCard(Patient patient, String filePath);
}
