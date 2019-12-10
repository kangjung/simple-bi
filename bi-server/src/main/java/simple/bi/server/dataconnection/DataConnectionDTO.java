package simple.bi.server.dataconnection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DataConnectionDTO {
  @NotNull
  @NotEmpty
  private String name;

  @NotNull
  @NotEmpty
  private String userName;

  @NotNull
  @NotEmpty
  private String password;

  @NotNull
  @NotEmpty
  private String url;

  @NotNull
  @NotEmpty
  private String dbType;
}
