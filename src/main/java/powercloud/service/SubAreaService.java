package powercloud.service;

import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import powercloud.exception.AreaInvalidException;
import powercloud.exception.AreaNotFoundException;
import powercloud.exception.SubAreaInvalidException;
import powercloud.exception.SubAreaNotFoundException;
import powercloud.model.SubArea;
import powercloud.repository.SubAreaRepository;
import powercloud.utility.AreaUtility;
import powercloud.utility.SubAreaUtility;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SubAreaService {

    private final SubAreaRepository repository;

    @Autowired
    public SubAreaService(SubAreaRepository repository) {
        this.repository = repository;
    }

    public List<SubArea> findAll() {
        return repository.findAll();
    }

    public SubArea findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new SubAreaNotFoundException("SubArea not found."));
    }

    @Synchronized
    public SubArea save(SubArea subArea) {
        if(SubAreaUtility.isSubAreaValid(subArea) == false)
            throw new SubAreaInvalidException("SubArea invalid.");

        return repository.save(subArea);
    }

    @Synchronized
    public SubArea update(SubArea subArea) {
        if(repository.existsById(subArea.getId()) == false)
            throw new SubAreaNotFoundException("SubArea not found.");

        return repository.save(subArea);
    }

    public void saveAll(List<SubArea> areas) {
        repository.saveAll(areas);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    @Synchronized
    public void deleteById(Long id) {
        if(repository.existsById(id) == false)
            throw new SubAreaNotFoundException("SubArea not found.");

        repository.deleteById(id);
    }

    public SubArea getMaxRevenue() {
        List<SubArea> subAreas = repository.findAll();
        Optional<SubArea> subArea = subAreas
                                        .stream()
                                        .max(Comparator.comparing(SubArea::getRevenue));
        return subArea.get();
    }

    public SubArea getMinRevenue() {
        List<SubArea> subAreas = repository.findAll();
        Optional<SubArea> subArea = subAreas
                                        .stream()
                                        .min(Comparator.comparing(SubArea::getRevenue));
        return subArea.get();
    }

}
