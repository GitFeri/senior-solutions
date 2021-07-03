package musicstore;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstrumentDTO {

    private Long id;
    private String brand;
    private InstrumentType type;
    private int price;
    private LocalDate postDate;
}
