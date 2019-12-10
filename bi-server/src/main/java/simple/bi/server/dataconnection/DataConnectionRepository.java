package simple.bi.server.dataconnection;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DataConnectionRepository extends JpaRepository<DataConnectionEntity, Long> {
}
