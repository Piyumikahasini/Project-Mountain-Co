package lk.ijse.projectggmountain.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class Event {
    private String id;
    private String name;
    private String date;
    private String time;
    private String type;
    private String empId;
}
