package be.technobel.corder.bl.impl;

import be.technobel.corder.bl.UtilFrontService;
import be.technobel.corder.dal.models.UtilFront;
import be.technobel.corder.dal.repositories.UtilFrontRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UtilFrontServiceImpl implements UtilFrontService {

    private final UtilFrontRepository utilFrontRepository;

    public UtilFrontServiceImpl(UtilFrontRepository utilFrontRepository) {
        this.utilFrontRepository = utilFrontRepository;
    }

    @Override
    public void create(UtilFront utilFront) {
        UtilFront entity = new UtilFront();
        entity.setTypeUtil(utilFront.getTypeUtil());
        entity.setContentUtil(utilFront.getContentUtil());

        utilFrontRepository.save(entity);

    }
    @Override
    public void update(Long id, UtilFront utilFront) {
        UtilFront entity = utilFrontRepository.findById(id).orElseThrow(()-> new NotFoundException("Participation non trouvée"));
        entity.setTypeUtil(utilFront.getTypeUtil());
        entity.setContentUtil(utilFront.getContentUtil());

        utilFrontRepository.save(entity);
    }

    @Override
    public UtilFront findById(Long id) {
        return utilFrontRepository.findById(id).orElseThrow(()-> new NotFoundException("Participation non trouvée"));
    }

    @Override
    public List<UtilFront> findAll() {
        return utilFrontRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        utilFrontRepository.deleteById(id);
    }
}