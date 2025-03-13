package CPM.cpm_posting.services;

import CPM.cpm_posting.dto.inRequestPosting;
import CPM.cpm_posting.dto.outRequestPosting;
import CPM.cpm_posting.dto.outResponsePosting;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class OutRequestPostingService {

    private final TokenService tokenService;

    public OutRequestPostingService(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public outRequestPosting createOutRequestPosting(inRequestPosting req) {
        String no_trace = req.getRrn().substring(req.getRrn().length() - 6);

        outRequestPosting requestBody = outRequestPosting.builder()
                .AUTH_TOKEN(tokenService.generateToken(outRequestPosting.builder().build()).getAUTH_TOKEN())
                .REF_NO(UUID.randomUUID().toString())
                .REQUEST_TIME(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .MODULE_CODE("710002")
                .CHANNEL_ID("MDW-SMB-0002")
                .ACC_DBT(req.getCustAccountNumber())
                .ACC_CDT("QRIS_PARAMETERS.BANK.TITIPAN_QRIS_DIGI_PRIMA")
                .AMOUNT(req.getTotalAmount().toString())
                .COD_DBT("424")
                .COD_CDT("924")
                .REF_TRX(req.getRefNoTrx())
                .REF_1("QRP-M001-" + no_trace)
                .REF_2("QRP-M001-" + no_trace)
                .NAR_1("PURCHASE" + req.getCpan())
                .NAR_2("MPAN" + req.getMpan() + req.getMerchantName())
                .NAR_3(req.getMerchantAccountNo() + req.getMerchantName())
                .NAR_5("PURCHASE" + req.getCpan())
                .NAR_6("MPAN" + req.getMpan() + req.getMerchantName())
                .NAR_7(req.getMerchantAccountNo() + req.getMerchantName())
                .build();

        return requestBody;
    }

    public outResponsePosting processRequest(outRequestPosting request) {
        // Process the request and generate a response
        outResponsePosting response = outResponsePosting.builder()
                .AUTH_TOKEN(request.getAUTH_TOKEN())
                .REF_NO(request.getREF_NO())
                .REQUEST_TIME(request.getREQUEST_TIME())
                .MODULE_CODE(request.getMODULE_CODE())
                .CHANNEL_ID(request.getCHANNEL_ID())
                .ACC_DBT(request.getACC_DBT())
                .ACC_CDT(request.getACC_CDT())
                .AMOUNT(request.getAMOUNT().toString())
                .COD_DBT(request.getCOD_DBT())
                .COD_CDT(request.getCOD_CDT())
                .REF_TRX(request.getREF_TRX())
                .REF_1(request.getREF_1())
                .REF_2(request.getREF_2())
                .NAR_1(request.getNAR_1())
                .NAR_2(request.getNAR_2())
                .NAR_3(request.getNAR_3())
                .NAR_5(request.getNAR_5())
                .NAR_6(request.getNAR_6())
                .NAR_7(request.getNAR_7())
                .build();

        return response;
    }
}