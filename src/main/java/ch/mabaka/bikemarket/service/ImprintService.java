package ch.mabaka.bikemarket.service;

import ch.mabaka.bikemarket.model.Imprint;
import ch.mabaka.bikemarket.repository.ImprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImprintService {
    @Autowired
    private ImprintRepository imprintRepository;

    public Imprint getImprint(String language) {
        // Versuche zuerst das Impressum für die gewünschte Sprache
        Optional<Imprint> imprintOpt = imprintRepository.findAll().stream()
            .filter(i -> i.getLanguage().equals(language))
            .findFirst();
        if (imprintOpt.isPresent()) {
            return imprintOpt.get();
        }
        // Fallback: Deutsch
        Optional<Imprint> germanImprint = imprintRepository.findAll().stream()
            .filter(i -> i.getLanguage().equals("de"))
            .findFirst();
        if (germanImprint.isPresent()) {
            return germanImprint.get();
        }
        // Falls auch kein deutsches vorhanden ist, lege es an
        Imprint imprint = new Imprint();
        imprint.setLanguage("de");
        imprint.setContent("Impressumstext wird hier angezeigt.");
        return imprintRepository.save(imprint);
    }

    public void updateImprint(String language, String content) {
        Imprint imprint = getImprint(language);
        imprint.setContent(content);
        imprintRepository.save(imprint);
    }
}