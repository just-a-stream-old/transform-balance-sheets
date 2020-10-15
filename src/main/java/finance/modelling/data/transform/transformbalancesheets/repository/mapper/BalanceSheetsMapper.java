package finance.modelling.data.transform.transformbalancesheets.repository.mapper;

import finance.modelling.fmcommons.data.schema.fmp.dto.FmpBalanceSheetDTO;
import finance.modelling.fmcommons.data.schema.fmp.dto.FmpBalanceSheetsDTO;
import finance.modelling.fmcommons.data.schema.model.BalanceSheet;
import finance.modelling.fmcommons.data.schema.model.BalanceSheets;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BalanceSheetsMapper {
    BalanceSheetsMapper INSTANCE = Mappers.getMapper(BalanceSheetsMapper.class);

    BalanceSheets balanceSheetsDTOToBalanceSheets(FmpBalanceSheetsDTO fmpBalanceSheetsDTO);

    @Mapping(source = "othertotalStockholdersEquity", target = "otherTotalStockholdersEquity")
    BalanceSheet balanceSheetDTOToBalanceSheet(FmpBalanceSheetDTO fmpBalanceSheetDTO);
}
