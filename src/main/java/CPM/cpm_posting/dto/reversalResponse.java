package CPM.cpm_posting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class reversalResponse {
    private String responseCode;
    private String responseDesc;
    private String timestamp;
    private String reversalPostingId;
}
