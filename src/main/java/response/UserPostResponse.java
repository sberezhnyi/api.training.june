package response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import request.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPostResponse {

    private Integer code;
    private Object meta;
    private User data;
}
