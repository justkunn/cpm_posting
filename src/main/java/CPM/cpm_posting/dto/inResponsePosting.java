package CPM.cpm_posting.dto;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class inResponsePosting implements Serializable {
    private String responseCode;

    private String responseDesc;

    private String result;

    private String timestamp;

    private String postingId;
}