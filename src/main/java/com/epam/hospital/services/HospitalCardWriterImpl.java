package com.epam.hospital.services;

import com.epam.hospital.data_access_layer.models.HospitalCard;
import com.epam.hospital.data_access_layer.models.Patient;
import com.epam.hospital.utils.ApplicationPropertiesLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;


@Slf4j
public class HospitalCardWriterImpl implements HospitalCardWriter {


    @Override
    public void writeHospitalCard(Patient patient, List<HospitalCard> hospitalCardList) {
        Path cardPath = Paths.get(ApplicationPropertiesLoader.load().getProperty("hospitalCard.storagePath"));
        try {
            Files.deleteIfExists(cardPath);
            Files.createFile(cardPath);
            Files.write(cardPath, Collections.singleton("Patient: " + patient.getFirstName() + " " + patient.getLastName()));
            for (HospitalCard hospitalCard : hospitalCardList) {
                Files.write(cardPath, Collections.singleton("\nDoctor/Nurse: " + hospitalCard.getDoctor().getFirstName() +
                        " " + hospitalCard.getDoctor().getLastName() + " ("
                        + hospitalCard.getDoctor().getCategory().getName() + ")\n"), StandardOpenOption.APPEND);
                Files.write(cardPath, Collections.singleton("Record date: "
                        + hospitalCard.getRecordDate().toString() + "\n"), StandardOpenOption.APPEND);
                if (hospitalCard.getProcedures() != null) {
                    Files.write(cardPath, Collections.singleton("Procedures: "
                            + hospitalCard.getProcedures() + "\n"), StandardOpenOption.APPEND);
                }
                if (hospitalCard.getMedications() != null) {
                    Files.write(cardPath, Collections.singleton("Medications: "
                            + hospitalCard.getMedications() + "\n"), StandardOpenOption.APPEND);
                }
                if (hospitalCard.getOperations() != null) {
                    Files.write(cardPath, Collections.singleton("Operations: "
                            + hospitalCard.getOperations() + "\n"), StandardOpenOption.APPEND);
                }
                if (hospitalCard.getDiagnosis() != null) {
                    Files.write(cardPath, Collections.singleton("Diagnosis: "
                            + hospitalCard.getDiagnosis() + "\n"), StandardOpenOption.APPEND);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
