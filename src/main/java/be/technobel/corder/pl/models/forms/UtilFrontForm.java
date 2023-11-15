package be.technobel.corder.pl.models.forms;

import be.technobel.corder.dal.models.UtilFront;

public class UtilFrontForm {

    private String typeUtil;
    private String contentUtil;

    public UtilFront toEntity() {
        UtilFront utilFront = new UtilFront();
        utilFront.setTypeUtil(this.typeUtil);
        utilFront.setContentUtil(this.contentUtil);
        return utilFront;
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
