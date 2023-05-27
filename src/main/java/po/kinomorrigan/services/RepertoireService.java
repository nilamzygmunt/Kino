package po.kinomorrigan.services;

import org.springframework.stereotype.Service;
import po.kinomorrigan.models.Repertoire;
import po.kinomorrigan.models.repositories.RepertoireRepository;

import java.util.List;

@Service
public class RepertoireService {
    private RepertoireRepository repertoireRepository;

    public RepertoireService(RepertoireRepository repertoireRepository) {
        this.repertoireRepository = repertoireRepository;
    }

    public List<Repertoire> getRepertoireList() {
        return repertoireRepository.findAll();
    }
}
