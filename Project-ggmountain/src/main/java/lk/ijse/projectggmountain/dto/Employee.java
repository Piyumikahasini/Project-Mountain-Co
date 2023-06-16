package lk.ijse.projectggmountain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {
    private String id;
    private String name;
    private String nic;
    private String dob;
    private String address;
    private String email;
    private String job;
    private String contact;
    private Double salary;
}
