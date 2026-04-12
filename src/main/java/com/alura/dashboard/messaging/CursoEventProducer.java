package com.alura.dashboard.messaging;

import com.alura.dashboard.dto.CursoConsultaEventoDTO;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class CursoEventProducer {

    public static final String QUEUE_NAME = "curso.consultas";

    private final JmsTemplate jmsTemplate;

    public CursoEventProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void publish(CursoConsultaEventoDTO evento) {
        jmsTemplate.convertAndSend(QUEUE_NAME, evento);
    }
}
