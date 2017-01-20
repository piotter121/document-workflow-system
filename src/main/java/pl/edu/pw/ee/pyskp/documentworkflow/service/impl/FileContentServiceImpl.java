package pl.edu.pw.ee.pyskp.documentworkflow.service.impl;

import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.FileContent;
import pl.edu.pw.ee.pyskp.documentworkflow.repository.FileContentRepository;
import pl.edu.pw.ee.pyskp.documentworkflow.service.FileContentService;

import java.util.Optional;

/**
 * Created by piotr on 20.01.17.
 */
@Service
public class FileContentServiceImpl implements FileContentService {
    private final FileContentRepository fileContentRepository;

    public FileContentServiceImpl(FileContentRepository fileContentRepository) {
        this.fileContentRepository = fileContentRepository;
    }

    @Override
    public Optional<FileContent> getOneById(long id) {
        return Optional.ofNullable(fileContentRepository.findOne(id));
    }
}
