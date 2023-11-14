package be.technobel.corder.dal.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UtilFront {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String typeUtil;
    private String contentUtil;

    public UtilFront() {
    }

    public Long getId() {
        return id;
    }

    public String getTypeUtil() {
        return typeUtil;
    }

    public void setTypeUtil(String typeUtil) {
        this.typeUtil = typeUtil;
    }

    public String getContentUtil() {
        return contentUtil;
    }

    public void setContentUtil(String contentUtil) {
        this.contentUtil = contentUtil;
    }
}
