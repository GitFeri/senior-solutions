package musicstore;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInstrumentCommand {

    @NotBlank(message = "Name cannot be blank!")
    private String brand;
    private InstrumentType type;

    @Positive(message = "Price cannot be negative")
    private int price;
}
