package com.chromamon.analysis.ieee.writer;

import com.chromamon.analysis.ieee.model.DiagnosticData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DiagnosticResultWriter implements ItemWriter<DiagnosticData> {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void write(Chunk<? extends DiagnosticData> items) throws Exception {
        for (DiagnosticData result : items) {
            rabbitTemplate.convertAndSend("diagnostic-results", result);
        }
    }
}