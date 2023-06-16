package lk.ijse.projectggmountain.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerTM {
    private String id;
    private String name;
    private String address;
    private String email;
    private String contact;
}
