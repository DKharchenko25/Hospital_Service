package com.epam.hospital.services;

import com.epam.hospital.data_access_layer.models.HospitalCard;
import com.epam.hospital.data_access_layer.models.Patient;

import java.util.List;

public interface HospitalCardWriter {

    void writeHospitalCard(Patient patient, List<HospitalCard> hospitalCardList);
}
