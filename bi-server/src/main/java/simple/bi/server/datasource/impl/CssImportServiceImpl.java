package simple.bi.server.datasource.impl;

import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.stereotype.Service;
import simple.bi.server.datasource.CssImportService;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class CssImportServiceImpl implements CssImportService {
  private final static int BOM_BYTES_NUM;

  static {
    BOM_BYTES_NUM = 4;
  }

  @Override
  public void importCsv(String filePath) throws IOException {
    try (
      FileChannel fileChannel = FileChannel.open(
        Paths.get(filePath),
        StandardOpenOption.READ
      )
    ) {
      ByteBuffer jvmHeapByteBuffer = ByteBuffer.allocate(100);
      int read;
      while (true) {
        read = fileChannel.read(jvmHeapByteBuffer);
        if (read == -1) {
          break;
        }
        jvmHeapByteBuffer.flip();
        String charset = this.getDetectCharset(jvmHeapByteBuffer);
        if (charset != null) {
          // TODO(kuckjwi): read data.
        }
        jvmHeapByteBuffer.clear();
      }
    }
  }
  @Override
  public char detectSeparator() {
    return 0;
  }

  /**
   * Detected charset.
   *
   * @param buffer ByteBuffer
   * @return charset (Can be null.)
   * @throws IOException
   */
  private String getDetectCharset(ByteBuffer buffer) throws IOException {
    UniversalDetector detector = new UniversalDetector();
    byte[] byteOrderMark = new byte[BOM_BYTES_NUM];
    buffer.get(byteOrderMark, 0, BOM_BYTES_NUM);
    detector.handleData(byteOrderMark);
    detector.dataEnd();
    detector.reset();
    return detector.getDetectedCharset();
  }
}
