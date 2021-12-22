package io.hoon.datacollector.writers;

import io.hoon.datacollector.dto.CollectedDataDto;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DBEachRowWriter implements Writer{

    @Override
    public boolean isWritable() {
        //TODO
        return false;
    }

    @Override
    public void write(Collection<CollectedDataDto> dataCollection) throws Exception {
        //TODO
    }
}
