package finance.modelling.data.transform.transformbalancesheets.service;

import finance.modelling.fmcommons.data.schema.fmp.dto.FmpBalanceSheetsDTO;
import org.apache.kafka.streams.kstream.KStream;

import java.util.function.Consumer;

public interface BalanceSheetDataModelService {
    Consumer<KStream<String, FmpBalanceSheetsDTO>> generateBalanceSheetDataModel();
}
