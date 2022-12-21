package com.epam.hospital.repositories;

import com.epam.hospital.models.HospitalCard;

import java.util.List;
import java.util.Optional;

public interface HospitalCardRepository {

    void addHospitalCard(HospitalCard hospitalCard);

    Optional<HospitalCard> getHospitalCardById(long id);

    List<HospitalCard> getAllHospitalCards();

    List<HospitalCard> getAllPatientHospitalCardsById(long id);

    void deleteHospitalCardById(long id);

    void updateProceduresById(long id, String procedures);

    void updateMedicationsById(long id, String medications);

    void updateOperationsById(long id, String operations);

    void updateDiagnosisById(long id, String diagnosis);
}
