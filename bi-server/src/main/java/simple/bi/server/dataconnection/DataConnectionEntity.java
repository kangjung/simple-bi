package simple.bi.server.dataconnection;

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

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "url", nullable = false)
  private String url;

  @Column(name = "create_date")
  private LocalDateTime createDate;

  @PrePersist
  public void onCreate() {
    if (this.createDate == null) {
      this.createDate = LocalDateTime.now();
    }
  }
}
