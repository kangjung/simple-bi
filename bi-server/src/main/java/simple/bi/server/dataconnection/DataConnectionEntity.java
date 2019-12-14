package simple.bi.server.dataconnection;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="dataconnection")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DataConnectionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "user_name", nullable = false)
  private String userName;

  // TODO(kuckjwi): encryption
  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "url", nullable = false)
  private String url;

  @Column(name = "db_type")
  private String dbType;

  @Column(name = "create_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDate;

  @PrePersist
  public void onCreate() {
    if (this.createDate == null) {
      this.createDate = LocalDateTime.now();
    }
  }
}
