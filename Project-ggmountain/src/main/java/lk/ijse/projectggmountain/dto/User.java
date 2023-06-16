package lk.ijse.projectggmountain.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private String username;
    private String password;
    private String jobtitle;
    private String email;
}
