package musicstore;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class MusicStoreService {

    private List<Instrument> instruments = new ArrayList<>();
    private AtomicLong id = new AtomicLong();
    private ModelMapper modelMapper;

    public MusicStoreService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    private Instrument findById(long id) {
        return instruments.stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find."));

    }

    public List<InstrumentDTO> getInstruments(Optional<String> brand, Optional<Integer> price) {

        Type targetListType = new TypeToken<List<InstrumentDTO>>() {
        }.getType();

        List<Instrument> filtered = instruments.stream()
                .filter(i -> brand.isEmpty() || i.getBrand().equalsIgnoreCase(brand.get()))
                .filter(i -> price.isEmpty() || i.getPrice() == price.get())
                .collect(Collectors.toList());

        return modelMapper.map(filtered, targetListType);
    }

    public InstrumentDTO getInstrument(long id) {
        return modelMapper.map(findById(id), InstrumentDTO.class);
    }

    public InstrumentDTO createInstrument(CreateInstrumentCommand command) {
        Instrument instrument = new Instrument(id.incrementAndGet(), command.getBrand(), command.getType(), command.getPrice(), LocalDate.now());
        instruments.add(instrument);
        return modelMapper.map(instrument, InstrumentDTO.class);
    }

    public void deleteAll() {
        instruments.clear();
        id = new AtomicLong();
    }

    public InstrumentDTO updatePrice(long id, UpdatePriceCommand command) {
        Instrument instrument = findById(id);

        if (instrument.getPrice() != command.getPrice()) {
            instrument.setPrice(command.getPrice());
            instrument.setPostDate(LocalDate.now());
        }

        return modelMapper.map(instrument, InstrumentDTO.class);

    }

    public InstrumentDTO findInstrumentById(long id) {

        Instrument instrument = findById(id);
        return modelMapper.map(instrument,InstrumentDTO.class);

    }
}

