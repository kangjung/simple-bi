package simple.bi.server.upload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import simple.bi.server.SimpleBiConfig;

@Service
public class FileUploadService {
  @Autowired
  private SimpleBiConfig simpleBiConfig;

  public void upload(MultipartFile multipartFile) {
    // TODO(kuckjwi) multipart.
  }
}
