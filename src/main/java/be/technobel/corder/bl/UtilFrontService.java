package be.technobel.corder.bl;

import be.technobel.corder.dal.models.UtilFront;

import java.util.List;

public interface UtilFrontService {
    UtilFront create(UtilFront utilFront);
    UtilFront update(Long id, UtilFront utilFront);
    UtilFront findById(Long id);
    List<UtilFront> findAll();
    UtilFront delete(Long id);
}
