package be.technobel.corder.pl.models;

import be.technobel.corder.dal.models.Address;
import jakarta.persistence.*;

import java.sql.Blob;
import java.time.LocalDate;

public class ParticipationForm {


    private LocalDate participationDate;
    private String participantFirstName;
    private String participantLastName;
    private String participantEmail;

    private Address participantAddress;
    private boolean validated;
    private boolean shipped;
    private String pictureName;
    private String pictureType;

    private Blob blob;

    public LocalDate getParticipationDate() {
        return participationDate;
    }

    public void setParticipationDate(LocalDate participationDate) {
        this.participationDate = participationDate;
    }

    public String getParticipantFirstName() {
        return participantFirstName;
    }

    public void setParticipantFirstName(String participantFirstName) {
        this.participantFirstName = participantFirstName;
    }

    public String getParticipantLastName() {
        return participantLastName;
    }

    public void setParticipantLastName(String participantLastName) {
        this.participantLastName = participantLastName;
    }

    public String getParticipantEmail() {
        return participantEmail;
    }

    public void setParticipantEmail(String participantEmail) {
        this.participantEmail = participantEmail;
    }

    public Address getParticipantAddress() {
        return participantAddress;
    }

    public void setParticipantAddress(Address participantAddress) {
        this.participantAddress = participantAddress;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public boolean isShipped() {
        return shipped;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }

    public String getPictureType() {
        return pictureType;
    }

    public void setPictureType(String pictureType) {
        this.pictureType = pictureType;
    }

    public Blob getBlob() {
        return blob;
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }
}
