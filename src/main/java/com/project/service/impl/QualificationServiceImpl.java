package com.project.service.impl;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Doctor;
import com.project.model.Qualification;
import com.project.model.dto.QualificationDTO;
import com.project.persistence.impl.DoctorRepository;
import com.project.persistence.impl.QualificationRepository;
import com.project.service.QualificationService;
import com.project.service.adapter.QualificationObjectAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class QualificationServiceImpl implements QualificationService {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private QualificationRepository repoQualification;

    @Autowired
    private DoctorRepository repoDoctor;

    @Override
    public List<Qualification> findQualifications() {
        List<Qualification> qualifications=new ArrayList<>();
        repoQualification.getAll().forEach(qualifications::add);
        logger.log(AbstractLogger.DEBUG, MessageFormat.format("{0} - Retrieve all qaulifications",QualificationServiceImpl.class));
        return qualifications;
    }

    @Override
    public Qualification save(QualificationDTO qDTO) {
        QualificationObjectAdapter qualificationObjectAdapter=new QualificationObjectAdapter(repoDoctor);
        Qualification qualification=(Qualification)qualificationObjectAdapter.convertFromClientToModel(qDTO);
        logger.log(AbstractLogger.DEBUG, MessageFormat.format("{0} - Saved review",QualificationServiceImpl.class));
        return repoQualification.save(qualification);
    }

    @Override
    public Qualification findQualification(int idDoctor) {
        Doctor doc=repoDoctor.findOne(idDoctor);
        if(doc!=null){
            logger.log(AbstractLogger.DEBUG, MessageFormat.format("{0} - Retrieve qualification",QualificationServiceImpl.class));
            return repoQualification.findOne(doc.getId());
        }
        logger.log(AbstractLogger.DEBUG, MessageFormat.format("{0} - Qualification not found",QualificationServiceImpl.class));
        return null;
    }
}
