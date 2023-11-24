package be.technobel.corder.dal.models;

import be.technobel.corder.dal.models.enums.Products;
import be.technobel.corder.dal.models.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table (
        uniqueConstraints =  @UniqueConstraint(name = "uniquePartcipant", columnNames = {
                "participantFirstname",
                "participantLastName",
                "participantEmail"})
)
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Temporal(value = TemporalType.DATE)
    private LocalDate participationDate;
    private String participantFirstName;
    private String participantLastName;
    @Column(unique = true)
    private String participantEmail;
    @OneToOne
    @JoinColumn(referencedColumnName = "address_id", name = "address_id")
    @Cascade(CascadeType.PERSIST)
    private Address participantAddress;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String pictureName;
    private String pictureType;
    @Lob
    private byte[] blob;

    @Enumerated(EnumType.STRING)
    private Products productType;
    @Min(1)
    @Max(3)
    private Long satisfaction;


    public Participation() {
    }

    public Long getId() {
        return id;
    }

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

    public byte[] getBlob() {
        return blob;
    }

    public void setBlob(byte[] blob) {
        this.blob = blob;
    }

    public Products getProductType() {
        return productType;
    }

    public void setProductType(Products productType) {
        this.productType = productType;
    }

    public Long getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Long satisfaction) {
        this.satisfaction = satisfaction;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
