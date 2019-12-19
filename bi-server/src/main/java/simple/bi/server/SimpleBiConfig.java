package simple.bi.server;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "simple-bi")
public class SimpleBiConfig {
  @Getter
  @Setter
  private String fileUploadPath;

  @Getter
  @Setter
  private String secretKey;

  @Getter
  @Setter
  private int defaultResultLimit;
}
