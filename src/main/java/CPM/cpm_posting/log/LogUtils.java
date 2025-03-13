package CPM.cpm_posting.log;

import org.springframework.stereotype.Service;
import CPM.cpm_posting.entity.PostingEntity;
import CPM.cpm_posting.repository.PostingRepository;
import CPM.cpm_posting.dto.inRequestPosting;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LogUtils {
    private final PostingRepository lr;

    public PostingEntity saveRequest(inRequestPosting request) {
        PostingEntity logEntity = PostingEntity.builder()
            .transactionType(request.getTransactionType())
            .rrn(request.getRrn())
            .refNoTrx(request.getRefNoTrx())
            .cpan(request.getCpan())
            .mpan(request.getMpan())
            .issuerId(request.getIssuerId())
            .acquiringInstitutionId(request.getAcquiringInstitutionId())
            .custPhoneNumber(request.getCustPhoneNumber())
            .customerName(request.getCustomerName())
            .sofType(request.getSofType())
            .custAccountNumber(request.getCustAccountNumber())
            .pinUE(request.getPinUE())
            .merchantId(request.getMerchantId())
            .merchantName(request.getMerchantName())
            .merchantAccountNo(request.getMerchantAccountNo())
            .merchantAccountName(request.getMerchantAccountName())
            .totalAmount(request.getTotalAmount())
            .amountFee(request.getAmountFee())
            .mdrAmount(request.getMdrAmount())
            .acquireFee(request.getAcquireFee())
            .issuerFee(request.getIssuerFee())
            .switchingFee(request.getSwitchingFee())
            .standartFee(request.getStandartFee())
            .serviceFee(request.getServiceFee())
            .build();
    
        // Save to database
        return lr.save(logEntity);
    }
}