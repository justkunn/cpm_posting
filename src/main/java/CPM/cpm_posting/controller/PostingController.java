package CPM.cpm_posting.controller;

import CPM.cpm_posting.dto.inRequestPosting;
import CPM.cpm_posting.dto.inResponsePosting;
import CPM.cpm_posting.dto.outRequestPosting;
import CPM.cpm_posting.dto.outResponsePosting;
import CPM.cpm_posting.dto.reversalResponse;
import CPM.cpm_posting.services.PostingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostingController {
    private final PostingService postServices;

    @PostMapping("/posting")
    public ResponseEntity<inResponsePosting> createPosting(@RequestBody inRequestPosting request) {
        log.info("Received posting request with ID: {}", request.getRefNoTrx());
        inResponsePosting response = postServices.sendPosting(request);
        log.info("Sending response: {}", response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reversal")
    public ResponseEntity<reversalResponse> sendReversal(@RequestBody inResponsePosting request) {
        reversalResponse response = postServices.sendReversalResponse(request);
        return ResponseEntity.ok(response);
    }
}