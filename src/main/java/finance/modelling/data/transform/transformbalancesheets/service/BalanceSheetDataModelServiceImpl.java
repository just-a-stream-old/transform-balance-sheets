package finance.modelling.data.transform.transformbalancesheets.service;

import finance.modelling.data.transform.transformbalancesheets.repository.BalanceSheetRepository;
import finance.modelling.data.transform.transformbalancesheets.repository.config.MongoDbConfig;
import finance.modelling.data.transform.transformbalancesheets.repository.mapper.BalanceSheetsMapper;
import finance.modelling.data.transform.transformbalancesheets.service.config.TopicConfig;
import finance.modelling.fmcommons.data.logging.LogDb;
import finance.modelling.fmcommons.data.logging.kstream.LogMessageConsumed;
import finance.modelling.fmcommons.data.schema.fmp.dto.FmpBalanceSheetsDTO;
import finance.modelling.fmcommons.data.schema.model.BalanceSheets;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

@Service
public class BalanceSheetDataModelServiceImpl implements BalanceSheetDataModelService {

    private final TopicConfig topics;
    private final BalanceSheetRepository balanceSheetRepository;
    private final MongoDbConfig dbConfig;

    public BalanceSheetDataModelServiceImpl(
            TopicConfig topics,
            BalanceSheetRepository balanceSheetRepository,
            MongoDbConfig dbConfig) {
        this.topics = topics;
        this.balanceSheetRepository = balanceSheetRepository;
        this.dbConfig = dbConfig;
    }

    @Bean
    public Consumer<KStream<String, FmpBalanceSheetsDTO>> generateBalanceSheetDataModel() {
        return fmpBalanceSheets -> fmpBalanceSheets
                .transformValues(() -> new LogMessageConsumed<>(topics.getTraceIdHeaderName()))
                .mapValues(BalanceSheetsMapper.INSTANCE::balanceSheetsDTOToBalanceSheets)
                .foreach(this::saveToBalanceSheetRepository);
    }

    protected void saveToBalanceSheetRepository(String _key, BalanceSheets balanceSheets) {
        Mono
                .just(balanceSheets)
                .flatMap(balanceSheetRepository::save)
                .subscribe(
                        balanceSheetsMono -> LogDb.logInfoDataItemSaved(
                                BalanceSheets.class, balanceSheetsMono.getSymbol(), dbConfig.getDbUri()),
                        error -> LogDb.logErrorFailedDataItemSave(
                                BalanceSheets.class, error, dbConfig.getDbUri(), List.of("Log and fail"))
                );
    }
}
