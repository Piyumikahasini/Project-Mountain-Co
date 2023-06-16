package lk.ijse.projectggmountain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartDTO {
    private String itemId;
    private Integer orderQty;

}
