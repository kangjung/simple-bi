package simple.bi.server;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class ConfigLoadTests {
  @Autowired
  private SimpleBiConfig simpleBiConfig;

  @Test
  void loadConfigTest() throws IOException {
    assertThat(simpleBiConfig.getFileUploadPath()).isNotNull();
    assertThat(simpleBiConfig.getSecretKey()).isNotNull();
  }
}
