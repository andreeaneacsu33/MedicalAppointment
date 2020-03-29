package com.project.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST})
    @JoinColumn(name = "idDoctor")
    private Doctor doctor;

    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST})
    @JoinColumn(name="idPatient")
    private Patient patient;

    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST})
    @JoinColumn(name="idAffiliation")
    private Affiliation affiliation;

    @Column(name="startDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",timezone = "Europe/Bucharest")
    private Date startDate;

    @Column(name = "endDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm",timezone = "Europe/Bucharest")
    private Date endDate;

    @Column(name="notes")
    private String notes;

    @Column(name="title")
    private String title;


    public Appointment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Affiliation getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(Affiliation affiliation) {
        this.affiliation = affiliation;
    }

    @Override
    public String toString() {
        return "Appointment: no. " + id + " doctor: " + doctor + " patient: " + patient;
    }
}
