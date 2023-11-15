package be.technobel.corder.bl;

import be.technobel.corder.dal.models.UtilFront;

import java.util.List;

public interface UtilFrontService {
    void create(UtilFront utilFront);
    void update(Long id, UtilFront utilFront);
    UtilFront findById(Long id);
    List<UtilFront> findAll();
    void delete(Long id);
}