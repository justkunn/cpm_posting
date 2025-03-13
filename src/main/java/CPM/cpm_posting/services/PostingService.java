package CPM.cpm_posting.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import CPM.cpm_posting.dto.inRequestPosting;
import CPM.cpm_posting.dto.outRequestPosting;
import CPM.cpm_posting.dto.outResponsePosting;
import CPM.cpm_posting.dto.reversalResponse;
import CPM.cpm_posting.dto.inResponsePosting;
import CPM.cpm_posting.log.LogUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostingService {
    private final LogUtils logUtils;
    private final RestTemplate restTemplate = new RestTemplate();
    private final TokenService tokenService;
    
    private static final String URL = "http://192.168.227.149:8082/mdw/basic/tfrintrabank";
    // private static final String URL_REVERSAL = "http://192.168.227.149:8082/mdw/basic/tfrintrabank";

    public inResponsePosting sendPosting(inRequestPosting req) {
        inResponsePosting response = new inResponsePosting();
        ObjectMapper objectMapper = new ObjectMapper();
        String no_trace = req.getRrn().substring(req.getRrn().length() - 6);
        String timestamp = String.valueOf(Instant.now().getEpochSecond());

        try {
            // Create request body from outRequestPosting using data from inRequestPosting
            outRequestPosting requestBody = outRequestPosting.builder()
                    .AUTH_TOKEN(tokenService.generateToken(outRequestPosting.builder().build()).getAUTH_TOKEN())
                    .REF_NO(timestamp)
                    .REQUEST_TIME(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                    .MODULE_CODE("710018")
                    .CHANNEL_ID("MDW-PRTL-001")
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


            // Log request in JSON format for better readability
            log.info("Processing transaction: {}", objectMapper.writeValueAsString(requestBody));

            
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            // Create HttpEntity with headers
            HttpEntity<outRequestPosting> entity = new HttpEntity<>(requestBody, headers);
            
            // Send request to API
            ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
            log.info("responseEntity {}", responseEntity);
            String result = responseEntity.getBody();
            log.info("Request successfully sent!");
            
            // Save request log
            logUtils.saveRequest(req);

            // Log response
            log.info("Received transaction response: {}", result);

            Map<String, Object> resultMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
            });

            // Set response
            if ("00".equals(resultMap.get("responseCode"))) {
                response.setResponseCode("00");
                response.setResponseDesc("Success");
                response.setPostingId(UUID.randomUUID().toString());
            } else if ("22".equals(resultMap.get("responseCode"))) {
                response.setResponseCode("22");
                response.setResponseDesc("Failed, due to insufficient fund");
            } else if ("96".equals(resultMap.get("responseCode"))) {
                response.setResponseCode("96");
                response.setResponseDesc("Failed, due to any reason except insufficient fund");
            }
            response.setResult(result);

        } catch (Exception e) {
            log.error("Error processing transaction", e);
            response.setResponseCode("99");
            response.setResponseDesc("Failed");
        }

        return response;
    }

    public reversalResponse sendReversalResponse(inResponsePosting res) {
        reversalResponse response = new reversalResponse();

        try {
            inResponsePosting requestBody = inResponsePosting.builder()
                    .postingId(res.getPostingId())
                    .build();

            HttpEntity<inResponsePosting> entity = new HttpEntity<>(requestBody);

            String result = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class).getBody();

            // Log hasil response
            log.info("Response from API: {}", result);

            // Set response dari hasil API
            response.setResponseDesc(result);

        } catch (Exception e) {
            log.error("Error processing transaction: {}", e.getMessage(), e);
            response.setResponseDesc("Error: " + e.getMessage());
        }

        return response;
    }

    
}