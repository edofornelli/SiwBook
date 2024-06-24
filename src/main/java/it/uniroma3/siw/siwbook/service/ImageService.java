package it.uniroma3.siw.siwbook.service;

import it.uniroma3.siw.siwbook.model.Image;
import it.uniroma3.siw.siwbook.repository.ImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image getImage(Long id) {
        return this.imageRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteImage(Image image) {
        this.imageRepository.delete(image);
    }

    @Transactional
    public Image saveImage(Image image) {
        return this.imageRepository.save(image);
    }

}
