package request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    private Integer id;
    private String name;
    private String email;
    private String gender;
    private String status;
    private String createdAt;
    private String updatedAt;
}
