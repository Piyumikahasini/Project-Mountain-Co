package lk.ijse.projectggmountain.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventTM {
    String empId;
    String id;
    String name;
    String date;
    String time;
    String type;
}
