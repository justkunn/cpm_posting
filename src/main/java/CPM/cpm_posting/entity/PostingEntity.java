package CPM.cpm_posting.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionType;
    private String rrn;
    private String refNoTrx;
    private String cpan;
    private String mpan;
    private String issuerId;
    private String acquiringInstitutionId;
    private String custPhoneNumber;
    private String customerName;
    private String sofType;
    private String custAccountNumber;
    private String pinUE;
    private String merchantId;
    private String merchantName;
    private String merchantAccountNo;
    private String merchantAccountName;
    private BigDecimal totalAmount;
    private BigDecimal amountFee;
    private BigDecimal mdrAmount;
    private BigDecimal acquireFee;
    private BigDecimal issuerFee;
    private BigDecimal switchingFee;
    private BigDecimal standartFee;
    private BigDecimal serviceFee;
}
