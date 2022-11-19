package com.project.dinopedia.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ImageService {

    void addImagesToDinosaur(Long id, List<MultipartFile> files);

    void delete(Long id);
}
