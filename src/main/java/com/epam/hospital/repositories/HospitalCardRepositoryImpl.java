package com.epam.hospital.repositories;

import com.epam.hospital.daos.Dao;
import com.epam.hospital.models.HospitalCard;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class HospitalCardRepositoryImpl implements HospitalCardRepository {

    private final Dao<HospitalCard> hospitalCardDao;
    private final PatientRepository patientRepository;
    private final HospitalStaffRepository hospitalStaffRepository;

    public HospitalCardRepositoryImpl(Dao<HospitalCard> hospitalCardDao, PatientRepository patientRepository,
                                      HospitalStaffRepository hospitalStaffRepository) {
        this.hospitalCardDao = hospitalCardDao;
        this.patientRepository = patientRepository;
        this.hospitalStaffRepository = hospitalStaffRepository;
    }

    @Override
    public void addHospitalCard(HospitalCard hospitalCard) {
        hospitalCardDao.save(hospitalCard);
    }


    @Override
    public Optional<HospitalCard> getHospitalCardById(long id) {
        if (hospitalCardDao.findById(id).isPresent()) {
            HospitalCard hospitalCard = hospitalCardDao.findById(id).get();
            hospitalCard.setPatient(patientRepository.getPatientById(hospitalCard.getPatientId()).orElseThrow(IllegalArgumentException::new));
            hospitalCard.setDoctor(hospitalStaffRepository.getHospitalStaffById(hospitalCard.getDoctorId()).orElseThrow(IllegalArgumentException::new));
            return Optional.of(hospitalCard);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<HospitalCard> getAllHospitalCards() {
        List<HospitalCard> hospitalCards = hospitalCardDao.findAll();
        hospitalCards.forEach(hospitalCard -> {
            hospitalCard.setPatient(patientRepository.getPatientById(hospitalCard.getPatientId()).orElseThrow(IllegalArgumentException::new));
            hospitalCard.setDoctor(hospitalStaffRepository.getHospitalStaffById(hospitalCard.getDoctorId()).orElseThrow(IllegalArgumentException::new));
        });
        return hospitalCards;
    }

    @Override
    public List<HospitalCard> getAllPatientHospitalCardsById(long id) {
        return getAllHospitalCards().stream()
                .filter(hospitalCard -> hospitalCard.getPatient().getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteHospitalCardById(long id) {
        hospitalCardDao.deleteById(id);
    }

    @Override
    public void updateProceduresById(long id, String procedures) {
        if (hospitalCardDao.findById(id).isPresent()) {
            HospitalCard hospitalCard = hospitalCardDao.findById(id).get();
            hospitalCard.setProcedures(procedures);
            hospitalCardDao.update(hospitalCard);
        }
    }

    @Override
    public void updateMedicationsById(long id, String medications) {
        if (hospitalCardDao.findById(id).isPresent()) {
            HospitalCard hospitalCard = hospitalCardDao.findById(id).get();
            hospitalCard.setMedications(medications);
            hospitalCardDao.update(hospitalCard);
        }
    }

    @Override
    public void updateOperationsById(long id, String operations) {
        if (hospitalCardDao.findById(id).isPresent()) {
            HospitalCard hospitalCard = hospitalCardDao.findById(id).get();
            hospitalCard.setOperations(operations);
            hospitalCardDao.update(hospitalCard);
        }
    }

    @Override
    public void updateDiagnosisById(long id, String diagnosis) {
        if (hospitalCardDao.findById(id).isPresent()) {
            HospitalCard hospitalCard = hospitalCardDao.findById(id).get();
            hospitalCard.setDiagnosis(diagnosis);
            hospitalCardDao.update(hospitalCard);
        }
    }
}
