package finance.modelling.data.transform.transformbalancesheets.repository;

import finance.modelling.fmcommons.data.schema.model.BalanceSheets;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BalanceSheetRepository extends ReactiveMongoRepository<BalanceSheets, UUID> {
}
