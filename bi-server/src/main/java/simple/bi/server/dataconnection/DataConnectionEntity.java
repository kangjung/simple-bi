package simple.bi.server.dataconnection;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDate;

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
  private LocalDate createDate;

  @PrePersist
  public void onCreate() {
    if (this.createDate == null) {
      this.createDate = LocalDate.now();
    }
  }
}
