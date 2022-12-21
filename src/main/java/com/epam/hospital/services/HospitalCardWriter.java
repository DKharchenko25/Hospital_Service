package com.epam.hospital.services;

import com.epam.hospital.models.HospitalCard;
import com.epam.hospital.models.Patient;

import java.util.List;

public interface HospitalCardWriter {

    void writeHospitalCard(Patient patient, List<HospitalCard> hospitalCardList, String path);
}
