package lk.ijse.projectggmountain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewDelivery {
    private String orderId;
    private String delId;
    private String location;
    private String empId;
    private String dueDate;
}
