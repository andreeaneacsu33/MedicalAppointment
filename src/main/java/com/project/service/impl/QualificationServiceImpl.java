package com.project.service.impl;

import com.project.model.Doctor;
import com.project.model.Qualification;
import com.project.model.dto.QualificationDTO;
import com.project.persistence.impl.DoctorRepository;
import com.project.persistence.impl.QualificationRepository;
import com.project.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QualificationServiceImpl implements QualificationService {

    @Autowired
    private QualificationRepository repoQualification;

    @Autowired
    private DoctorRepository repoDoctor;

    @Override
    public List<Qualification> findQualifications() {
        List<Qualification> qualifications=new ArrayList<>();
        repoQualification.getAll().forEach(qualifications::add);
        return qualifications;
    }

    @Override
    public Qualification save(QualificationDTO qDTO) {
        Qualification qualification=new Qualification();
        qualification.setIdDoctor(repoDoctor.findOne(qDTO.getEmail()));
        qualification.setTitle(qDTO.getTitle());
        qualification.setInstitute(qDTO.getInstitute());
        qualification.setGraduationYear(qDTO.getGraduationYear());
        return repoQualification.save(qualification);
    }

    @Override
    public Qualification findQualification(int idDocotor) {
        Doctor doc=repoDoctor.findOne(idDocotor);
        if(doc!=null){
            return repoQualification.findOne(doc.getId());
        }
        return null;
    }
}
