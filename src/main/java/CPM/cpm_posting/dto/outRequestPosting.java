package CPM.cpm_posting.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class outRequestPosting {
    private String AUTH_TOKEN;
    private String REF_NO;
    private String REQUEST_TIME;
    private String MODULE_CODE;
    private String CHANNEL_ID;
    private String ACC_DBT;
    private String ACC_CDT;
    private String AMOUNT;
    private String COD_DBT;
    private String COD_CDT;
    private String REF_TRX;
    private String REF_1;
    private String REF_2;
    private String NAR_1;
    private String NAR_2;
    private String NAR_3;
    private String NAR_5;
    private String NAR_6;
    private String NAR_7;
}