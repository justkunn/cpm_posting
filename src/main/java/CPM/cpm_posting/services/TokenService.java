package CPM.cpm_posting.services;

import org.springframework.stereotype.Service;

import CPM.cpm_posting.dto.outRequestPosting;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TokenService {

    public outRequestPosting generateToken(outRequestPosting request) {
        String token = "testuat";
        String secret = Sha256Util.hash(token);
        String raw = request.getMODULE_CODE() + secret + request.getREQUEST_TIME() + request.getREF_NO() + request.getCHANNEL_ID();

        String hashed = Sha256Util.hash(raw);
        request.setAUTH_TOKEN(hashed);
        log.info("token -- {}", hashed);
        return request;
    }
}
