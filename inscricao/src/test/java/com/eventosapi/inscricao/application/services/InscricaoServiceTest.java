package com.eventosapi.inscricao.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.eventosapi.inscricao.application.dtos.FiltroInscricaoDTO;
import com.eventosapi.inscricao.application.exception.EntidadeNaoEncontradoException;
import com.eventosapi.inscricao.application.exception.RegraNegocioException;
import com.eventosapi.inscricao.application.port.EventoClientPort;
import com.eventosapi.inscricao.application.port.InscricaoRepositoryPort;
import com.eventosapi.inscricao.application.port.UsuarioClientPort;
import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Evento;
import com.eventosapi.inscricao.domain.models.Inscricao;
import com.eventosapi.inscricao.domain.models.Usuario;

import com.eventosapi.inscricao.interfaces.dto.InscricaoVoucherDTO;
import com.eventosapi.inscricao.application.port.InscricaoPublisherPort;
import com.eventosapi.inscricao.config.RabbitMQConfig;

import org.springframework.boot.test.mock.mockito.MockBean;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InscricaoServiceTest {

	
 	@MockBean
    private RabbitMQConfig rabbitMQConfig;

	
    @Mock
    InscricaoPublisherPort inscricaoPublisherPort;
	@Mock
	InscricaoRepositoryPort inscricaoRepo;
	@Mock
	EventoClientPort eventoPort;
	@Mock
	UsuarioClientPort usuarioPort;

	@InjectMocks
	InscricaoService service;

	@Mock
	Evento evento;
	@Mock
	Usuario usuario;

	@BeforeEach
	void setUp() {
		lenient().when(evento.getId()).thenReturn(1L);
		lenient().when(evento.getMaxParticipantes()).thenReturn(3);
		lenient().when(usuario.getId()).thenReturn(2L);
	}

	// @Test
	// void criar_deveSalvarEDevolverDTO_quandoOK() {
	// 	var now = LocalDateTime.now();
	// 	var inscricao = new Inscricao(1L, 1L, 2L, StatusInscricao.CONFIRMADA, now);

	// 	when(inscricaoRepo.existsByEventoAndUsuario(1L, 2L)).thenReturn(false);
	// 	when(eventoPort.findById(1L)).thenReturn(Optional.of(evento));
	// 	when(usuarioPort.existsById(2L)).thenReturn(true);
	// 	when(inscricaoRepo.countConfirmadasByEvento(1L)).thenReturn(0L);
	// 	when(inscricaoRepo.save(any(Inscricao.class))).thenReturn(inscricao);

	// 	Inscricao saved = service.salvar(inscricao);

	// 	assertThat(saved.getId()).isEqualTo(1L);
	// 	assertThat(saved.getEventoId()).isEqualTo(1L);
	// 	assertThat(saved.getUsuarioId()).isEqualTo(2L);
	// 	assertThat(saved.getStatus()).isEqualTo(StatusInscricao.CONFIRMADA);
	// 	assertThat(saved.getData()).isEqualTo(now);
	// }
	

	@Test
	void criar_deveSalvarEDevolverDTO_quandoOK() {
		var inscricao = new Inscricao(1L, 1L, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now());

		// Usando sua classe Evento
		var evento = new Evento(1L, "Evento Teste", 100);

		when(inscricaoRepo.existsByEventoAndUsuario(1L, 2L)).thenReturn(false);
		when(inscricaoRepo.countConfirmadasByEvento(1L)).thenReturn(1L);
		when(eventoPort.findById(1L)).thenReturn(Optional.of(evento));
		when(usuarioPort.existsById(2L)).thenReturn(true);
		when(inscricaoRepo.save(any())).thenReturn(inscricao);

		var result = service.salvar(inscricao);

		assertThat(result).isNotNull();
		assertThat(result.getEventoId()).isEqualTo(1L);
		assertThat(result.getUsuarioId()).isEqualTo(2L);
		verify(inscricaoPublisherPort).publicarInscricaoCriada(any());
	}



	// @Test
	// void criar_deveFalhar_quandoDuplicado() {
	// 	var inscricao = new Inscricao(1L, 1L, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now());

	// 	when(inscricaoRepo.existsByEventoAndUsuario(1L, 2L)).thenReturn(true);

	// 	assertThatThrownBy(() -> service.salvar(inscricao))
	// 			.isInstanceOf(RegraNegocioException.class)
	// 			.hasMessageContaining("já inscrito");
	// 	verifyNoMoreInteractions(eventoPort, usuarioPort);
	// }

	// @Test
	// void criar_deveFalhar_quandoCapacidadeEsgotada() {
	// 	var inscricao = new Inscricao(1L, 1L, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now());

	// 	when(inscricaoRepo.existsByEventoAndUsuario(1L, 2L)).thenReturn(false);
	// 	when(eventoPort.findById(1L)).thenReturn(Optional.of(evento));
	// 	when(usuarioPort.existsById(2L)).thenReturn(true);
	// 	when(inscricaoRepo.countConfirmadasByEvento(1L)).thenReturn(3L);

	// 	assertThatThrownBy(() -> service.salvar(inscricao))
	// 			.isInstanceOf(RegraNegocioException.class)
	// 			.hasMessageContaining("Capacidade esgotada");
	// 	verify(inscricaoRepo, never()).save(any());
	// }

	// @Test
	// void buscar_deveRetornarDTO_quandoExiste() {
	// 	var inscricao = new Inscricao(10L, 1L, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now());

	// 	when(inscricaoRepo.findById(10L)).thenReturn(Optional.of(inscricao));

	// 	var result = service.buscarPorId(10L);

	// 	assertThat(result.getId()).isEqualTo(10L);
	// 	assertThat(result.getEventoId()).isEqualTo(1L);
	// 	assertThat(result.getUsuarioId()).isEqualTo(2L);
	// 	assertThat(result.getStatus()).isEqualTo(StatusInscricao.CONFIRMADA);
	// 	assertThat(result.getData()).isNotNull();
	// }

	// @Test
	// void buscar_deveFalhar_quandoNaoExiste() {
	// 	when(inscricaoRepo.findById(999L)).thenReturn(Optional.empty());
	// 	assertThatThrownBy(() -> service.buscarPorId(999L))
	// 			.isInstanceOf(EntidadeNaoEncontradoException.class);
	// }

	// @Test
	// void listar_deveRetornarDTOs_semNecessidadeDeConsultarEventoUsuario() {
	// 	var filtro = new FiltroInscricaoDTO(null, null, null, null, null);
	// 	var i1 = new Inscricao(10L, 1L, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now());
	// 	var i2 = new Inscricao(11L, 1L, 2L, StatusInscricao.PENDENTE, LocalDateTime.now());
		
	// 	when(inscricaoRepo.findAll(any(FiltroInscricaoDTO.class), any(Pageable.class)))
	// 		.thenReturn(new PageImpl<>(List.of(i1, i2)));

	// 	var page = service.listar(filtro, PageRequest.of(0, 10));

	// 	assertThat(page).hasSize(2);
	// 	assertThat(page.getContent().get(0).getId()).isEqualTo(10L);
	// 	assertThat(page.getContent().get(0).getStatus()).isEqualTo(StatusInscricao.CONFIRMADA);
	// 	assertThat(page.getContent().get(1).getId()).isEqualTo(11L);
	// 	assertThat(page.getContent().get(1).getStatus()).isEqualTo(StatusInscricao.PENDENTE);

	// 	verifyNoInteractions(eventoPort, usuarioPort);
	// }

	// @Test
	// void atualizarStatus_deveAlterarParaCancelado() {
	// 	var existente = new Inscricao(10L, 1L, 2L, StatusInscricao.PENDENTE, LocalDateTime.now());

	// 	when(inscricaoRepo.findById(10L)).thenReturn(Optional.of(existente));
	// 	when(inscricaoRepo.save(any())).thenReturn(existente);

	// 	var result = service.atualizarStatus(10L, StatusInscricao.CANCELADO);

	// 	assertThat(result.getStatus()).isEqualTo(StatusInscricao.CANCELADO);
	// 	verifyNoInteractions(eventoPort, usuarioPort);
	// }

	// @Test
	// void excluir_deveMarcarCanceladoESalvar() {
	// 	var existente = new Inscricao(10L, 1L, 2L, StatusInscricao.PENDENTE, LocalDateTime.now());
		
	// 	when(inscricaoRepo.findById(10L)).thenReturn(Optional.of(existente));

	// 	service.excluir(10L);

	// 	ArgumentCaptor<Inscricao> captor = ArgumentCaptor.forClass(Inscricao.class);
	// 	verify(inscricaoRepo).save(captor.capture());
	// 	assertThat(captor.getValue().getStatus()).isEqualTo(StatusInscricao.CANCELADO);
	// 	verifyNoInteractions(eventoPort, usuarioPort);
	// }
	
@Test
    void criar_deveFalhar_quandoDuplicado() {
        var inscricao = new Inscricao(1L, 1L, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now());

        when(inscricaoRepo.existsByEventoAndUsuario(1L, 2L)).thenReturn(true);

        assertThatThrownBy(() -> service.salvar(inscricao))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessageContaining("já inscrito");
        verifyNoMoreInteractions(eventoPort, usuarioPort);
    }

    @Test
    void criar_deveFalhar_quandoCapacidadeEsgotada() {
        var inscricao = new Inscricao(1L, 1L, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now());

        when(inscricaoRepo.existsByEventoAndUsuario(1L, 2L)).thenReturn(false);
        when(eventoPort.findById(1L)).thenReturn(Optional.of(evento));
        when(usuarioPort.existsById(2L)).thenReturn(true);
        when(inscricaoRepo.countConfirmadasByEvento(1L)).thenReturn(3L);

        assertThatThrownBy(() -> service.salvar(inscricao))
                .isInstanceOf(RegraNegocioException.class)
                .hasMessageContaining("Capacidade esgotada");
        verify(inscricaoRepo, never()).save(any());
    }

    @Test
    void buscar_deveRetornarDTO_quandoExiste() {
        var inscricao = new Inscricao(10L, 1L, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now());

        when(inscricaoRepo.findById(10L)).thenReturn(Optional.of(inscricao));

        var result = service.buscarPorId(10L);

        assertThat(result.getId()).isEqualTo(10L);
        assertThat(result.getEventoId()).isEqualTo(1L);
        assertThat(result.getUsuarioId()).isEqualTo(2L);
        assertThat(result.getStatus()).isEqualTo(StatusInscricao.CONFIRMADA);
        assertThat(result.getData()).isNotNull();
    }

    @Test
    void buscar_deveFalhar_quandoNaoExiste() {
        when(inscricaoRepo.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(999L))
                .isInstanceOf(EntidadeNaoEncontradoException.class);
    }

    @Test
    void listar_deveRetornarDTOs_semNecessidadeDeConsultarEventoUsuario() {
        var filtro = new FiltroInscricaoDTO(null, null, null, null, null, null, null, null, null);
        var i1 = new Inscricao(10L, 1L, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now());
        var i2 = new Inscricao(11L, 1L, 2L, StatusInscricao.PENDENTE, LocalDateTime.now());

        when(inscricaoRepo.findAll(any(FiltroInscricaoDTO.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(i1, i2)));

        var page = service.listar(filtro, PageRequest.of(0, 10));

        assertThat(page).hasSize(2);
        assertThat(page.getContent().get(0).getId()).isEqualTo(10L);
        assertThat(page.getContent().get(0).getStatus()).isEqualTo(StatusInscricao.CONFIRMADA);
        assertThat(page.getContent().get(1).getId()).isEqualTo(11L);
        assertThat(page.getContent().get(1).getStatus()).isEqualTo(StatusInscricao.PENDENTE);

        verifyNoInteractions(eventoPort, usuarioPort);
    }

    @Test
    void atualizarStatus_deveAlterarParaCancelado() {
        var existente = new Inscricao(10L, 1L, 2L, StatusInscricao.PENDENTE, LocalDateTime.now());

        when(inscricaoRepo.findById(10L)).thenReturn(Optional.of(existente));
        when(inscricaoRepo.save(any())).thenReturn(existente);

        var result = service.atualizarStatus(10L, StatusInscricao.CANCELADO);

        assertThat(result.getStatus()).isEqualTo(StatusInscricao.CANCELADO);
        verifyNoInteractions(eventoPort, usuarioPort);
    }

    @Test
    void excluir_deveMarcarCanceladoESalvar() {
        var existente = new Inscricao(10L, 1L, 2L, StatusInscricao.PENDENTE, LocalDateTime.now());

        when(inscricaoRepo.findById(10L)).thenReturn(Optional.of(existente));

        service.excluir(10L);

        ArgumentCaptor<Inscricao> captor = ArgumentCaptor.forClass(Inscricao.class);
        verify(inscricaoRepo).save(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(StatusInscricao.CANCELADO);
        verifyNoInteractions(eventoPort, usuarioPort);
    }


}
