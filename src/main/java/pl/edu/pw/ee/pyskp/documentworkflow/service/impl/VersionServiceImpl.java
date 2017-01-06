package pl.edu.pw.ee.pyskp.documentworkflow.service.impl;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.FileContent;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.Version;
import pl.edu.pw.ee.pyskp.documentworkflow.dto.NewFileForm;
import pl.edu.pw.ee.pyskp.documentworkflow.service.DifferenceService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;
import pl.edu.pw.ee.pyskp.documentworkflow.service.VersionService;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by piotr on 06.01.17.
 */
@Service
public class VersionServiceImpl implements VersionService {
    private static final Logger logger = Logger.getLogger(VersionServiceImpl.class);

    private final UserService userService;
    private final DifferenceService differenceService;

    public VersionServiceImpl(UserService userService, DifferenceService differenceService) {
        this.userService = userService;
        this.differenceService = differenceService;
    }

    @Override
    public Version createUnmanagedInitVersionOfFile(NewFileForm form) throws IOException, TikaException {
        Version version = new Version();
        version.setVersionString(form.getVersionString());
        version.setMessage(form.getVersionMessage());
        version.setAuthor(userService.getCurrentUser());
        MultipartFile file = form.getFile();
        version.setCheckSum(calculateCheckSum(file.getBytes()));
        version.setSaveDate(new Date());
        FileContent fileContent = new FileContent();
        fileContent.setContent(file.getBytes());
        version.setFileContent(fileContent);
        version.setDifferences(differenceService.createDifferencesForNewFile(file.getInputStream()));
        return version;
    }

    private static String calculateCheckSum(byte[] bytes) {
        try {
            return String.format("%064x",
                    new BigInteger(MessageDigest.getInstance("SHA-256").digest(bytes)));
        } catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
