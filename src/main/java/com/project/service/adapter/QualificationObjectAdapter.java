package com.project.service.adapter;

import com.project.model.Qualification;
import com.project.model.dto.QualificationDTO;
import com.project.persistence.impl.DoctorRepository;

public class QualificationObjectAdapter implements ObjectAdapter {
    private DoctorRepository repoDoctor;

    public QualificationObjectAdapter(DoctorRepository repoDoctor) {
        this.repoDoctor = repoDoctor;
    }

    @Override
    public ModelObject convertFromClientToModel(ClientObject clientObject) {
        Qualification qualification=new Qualification();
        QualificationDTO qDTO=(QualificationDTO)clientObject;
        qualification.setIdDoctor(repoDoctor.findOne(qDTO.getEmail()));
        qualification.setTitle(qDTO.getTitle());
        qualification.setInstitute(qDTO.getInstitute());
        qualification.setGraduationYear(qDTO.getGraduationYear());
        return qualification;
    }
}
