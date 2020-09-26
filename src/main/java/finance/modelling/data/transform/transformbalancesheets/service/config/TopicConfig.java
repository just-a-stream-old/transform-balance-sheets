package finance.modelling.data.transform.transformbalancesheets.service.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TopicConfig {

    private final String traceIdHeaderName;
    private final String ingestFmpBalanceSheetTopic;

    public TopicConfig(
            @Value("${spring.cloud.stream.kafka.streams.header.traceId}") String traceIdHeaderName,
            @Value("${spring.cloud.stream.bindings.generateBalanceSheetDataModel-in-0.destination}") String ingestFmpBalanceSheetTopic) {
        this.traceIdHeaderName = traceIdHeaderName;
        this.ingestFmpBalanceSheetTopic = ingestFmpBalanceSheetTopic;
    }
}
