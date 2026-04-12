package com.alura.dashboard.messaging;

import com.alura.dashboard.dto.CursoConsultaEventoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class CursoEventListener {

    private static final Logger logger = LoggerFactory.getLogger(CursoEventListener.class);

    @JmsListener(destination = CursoEventProducer.QUEUE_NAME)
    public void receive(CursoConsultaEventoDTO evento) {
        logger.info("Evento de consulta recebido: tipo={}, categoriaId={}, usuario={}, dataHora={}",
                evento.getTipoConsulta(), evento.getCategoriaId(), evento.getUsuario(), evento.getDataHora());
    }
}
