package com.project.service.adapter;

import com.project.model.Affiliation;
import com.project.model.dto.AffiliationDTO;
import com.project.persistence.impl.DoctorRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AffiliationObjectAdapter implements ObjectAdapter {
    private DoctorRepository repoDoctor;

    public AffiliationObjectAdapter(DoctorRepository repoDoctor) {
        this.repoDoctor = repoDoctor;
    }

    @Override
    public ModelObject convertFromClientToModel(ClientObject clientObject) {
        try {
            Affiliation affiliation = new Affiliation();
            AffiliationDTO affiliationDTO = (AffiliationDTO) clientObject;
            affiliation.setIdDoctor(repoDoctor.findOne(affiliationDTO.getEmail()));
            affiliation.setCity(affiliationDTO.getCity());
            affiliation.setCountry(affiliationDTO.getCountry());
            affiliation.setHospitalName(affiliationDTO.getHospitalName());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = df.parse(affiliationDTO.getStartDate());
            affiliation.setStartDate(startDate);
            return affiliation;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
