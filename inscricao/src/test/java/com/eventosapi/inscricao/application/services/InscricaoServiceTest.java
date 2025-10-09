package com.eventosapi.inscricao.application.services;

import com.eventosapi.inscricao.application.exception.EntidadeNaoEncontradoException;
import com.eventosapi.inscricao.application.exception.RegraNegocioException;
import com.eventosapi.inscricao.application.port.EventoClientPort;
import com.eventosapi.inscricao.application.port.InscricaoRepositoryPort;
import com.eventosapi.inscricao.application.port.UsuarioClientPort;
import com.eventosapi.inscricao.domain.enums.StatusInscricao;
import com.eventosapi.inscricao.domain.models.Inscricao;
import com.eventosapi.inscricao.domain.models.EventoResumo;
import com.eventosapi.inscricao.domain.models.UsuarioResumo;
import com.eventosapi.inscricao.interfaces.dto.FiltroInscricaoDTO;
import com.eventosapi.inscricao.interfaces.dto.InscricaoRequestDTO;
import com.eventosapi.inscricao.interfaces.dto.InscricaoResponseDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class InscricaoServiceTest {

  @Mock InscricaoRepositoryPort inscricaoRepo;
  @Mock EventoClientPort eventoPort;
  @Mock UsuarioClientPort usuarioPort;

  @InjectMocks InscricaoService service;

  @Mock EventoResumo evento;
  @Mock UsuarioResumo usuario;

  @BeforeEach
  void setUp() {
    lenient().when(evento.getId()).thenReturn(1L);
    lenient().when(evento.getMaxParticipantes()).thenReturn(3);
    lenient().when(usuario.getId()).thenReturn(2L);
  }

  @Test
  void criar_deveSalvarEDevolverDTO_quandoOK() {
    var req = new InscricaoRequestDTO(1L, 2L, StatusInscricao.CONFIRMADA);

    when(inscricaoRepo.existsByEventoAndUsuario(1L, 2L)).thenReturn(false);
    when(eventoPort.findById(1L)).thenReturn(Optional.of(evento));
    when(usuarioPort.findById(2L)).thenReturn(Optional.of(usuario));
    when(inscricaoRepo.countConfirmadasByEvento(1L)).thenReturn(0L);

    ArgumentCaptor<Inscricao> captor = ArgumentCaptor.forClass(Inscricao.class);
    when(inscricaoRepo.save(captor.capture())).thenAnswer(inv -> {
      var i = inv.getArgument(0, Inscricao.class);
      // retorna uma NOVA instância com id preenchido (não depende de setId)
      return new Inscricao(10L, i.getEventoId(), i.getUsuarioId(), i.getStatus(), i.getData());
    });

    InscricaoResponseDTO resp = service.criar(req);

    assertThat(resp.id()).isEqualTo(10L);
    assertThat(resp.eventoId()).isEqualTo(1L);
    assertThat(resp.usuarioId()).isEqualTo(2L);
    assertThat(resp.status()).isEqualTo(StatusInscricao.CONFIRMADA);

    var salvo = captor.getValue();
    assertThat(salvo.getEventoId()).isEqualTo(1L);
    assertThat(salvo.getUsuarioId()).isEqualTo(2L);
  }

  @Test
  void criar_deveUsarStatusPENDENTE_quandoStatusNulo() {
    var req = new InscricaoRequestDTO(1L, 2L, null);

    when(inscricaoRepo.existsByEventoAndUsuario(1L, 2L)).thenReturn(false);
    when(eventoPort.findById(1L)).thenReturn(Optional.of(evento));
    when(usuarioPort.findById(2L)).thenReturn(Optional.of(usuario));
    when(inscricaoRepo.countConfirmadasByEvento(1L)).thenReturn(0L);
    when(inscricaoRepo.save(any())).thenAnswer(inv -> {
      var i = inv.getArgument(0, Inscricao.class);
      return new Inscricao(11L, i.getEventoId(), i.getUsuarioId(), i.getStatus(), i.getData());
    });

    var resp = service.criar(req);
    assertThat(resp.id()).isEqualTo(11L);
    assertThat(resp.status()).isEqualTo(StatusInscricao.PENDENTE);
  }

  @Test
  void criar_deveFalhar_quandoDuplicado() {
    var req = new InscricaoRequestDTO(1L, 2L, StatusInscricao.CONFIRMADA);
    when(inscricaoRepo.existsByEventoAndUsuario(1L, 2L)).thenReturn(true);

    assertThatThrownBy(() -> service.criar(req))
        .isInstanceOf(RegraNegocioException.class)
        .hasMessageContaining("já inscrito");
    verifyNoMoreInteractions(eventoPort, usuarioPort);
  }

  @Test
  void criar_deveFalhar_quandoCapacidadeEsgotada() {
    var req = new InscricaoRequestDTO(1L, 2L, StatusInscricao.CONFIRMADA);
    when(inscricaoRepo.existsByEventoAndUsuario(1L, 2L)).thenReturn(false);
    when(eventoPort.findById(1L)).thenReturn(Optional.of(evento));
    when(usuarioPort.findById(2L)).thenReturn(Optional.of(usuario));
    when(inscricaoRepo.countConfirmadasByEvento(1L)).thenReturn(3L);

    assertThatThrownBy(() -> service.criar(req))
        .isInstanceOf(RegraNegocioException.class)
        .hasMessageContaining("Capacidade esgotada");
    verify(inscricaoRepo, never()).save(any());
  }

  @Test
  void buscar_deveRetornarDTO_quandoExiste() {
    var insc = new Inscricao(10L, 1L, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now());
    when(inscricaoRepo.findById(10L)).thenReturn(Optional.of(insc));

    var resp = service.buscar(10L);

    assertThat(resp.id()).isEqualTo(10L);
    assertThat(resp.eventoId()).isEqualTo(1L);
    assertThat(resp.usuarioId()).isEqualTo(2L);
    assertThat(resp.status()).isEqualTo(StatusInscricao.CONFIRMADA);
    assertThat(resp.data()).isNotNull();
  }

  @Test
  void buscar_deveFalhar_quandoNaoExiste() {
    when(inscricaoRepo.findById(999L)).thenReturn(Optional.empty());
    assertThatThrownBy(() -> service.buscar(999L))
        .isInstanceOf(EntidadeNaoEncontradoException.class);
  }

  @Test
  void listar_deveRetornarDTOs_semNecessidadeDeConsultarEventoUsuario() {
    var i1 = new Inscricao(10L, 1L, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now());
    var i2 = new Inscricao(11L, 1L, 2L, StatusInscricao.PENDENTE, LocalDateTime.now());
    when(inscricaoRepo.findAll(null, null, null, null, null, 0, 10)).thenReturn(List.of(i1, i2));

    var lista = service.listar(new FiltroInscricaoDTO(null, null, null, null, null, 0, 10));

    assertThat(lista).hasSize(2);
    assertThat(lista.get(0).id()).isEqualTo(10L);
    assertThat(lista.get(1).status()).isEqualTo(StatusInscricao.PENDENTE);

    verifyNoInteractions(eventoPort, usuarioPort);
  }

  @Test
  void atualizarStatus_deveAlterarParaCancelado() {
    var existente = new Inscricao(10L, 1L, 2L, StatusInscricao.PENDENTE, LocalDateTime.now());
    when(inscricaoRepo.findById(10L)).thenReturn(Optional.of(existente));
    when(inscricaoRepo.save(any())).thenAnswer(inv -> {
      var i = inv.getArgument(0, Inscricao.class);
      return new Inscricao(i.getId(), i.getEventoId(), i.getUsuarioId(), i.getStatus(), i.getData());
    });

    var resp = service.atualizarStatus(10L, StatusInscricao.CANCELADO);
    assertThat(resp.status()).isEqualTo(StatusInscricao.CANCELADO);
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


  @Test
  void listarConfirmadasPorEvento_deveRetornarSomenteConfirmadas() {
    Long eventoId = 1L;
    var i1 = new Inscricao(10L, eventoId, 2L, StatusInscricao.CONFIRMADA, LocalDateTime.now());
    var i2 = new Inscricao(11L, eventoId, 3L, StatusInscricao.CONFIRMADA, LocalDateTime.now());

    when(inscricaoRepo.findByEventoAndStatus(eventoId, StatusInscricao.CONFIRMADA, 0, 10))
        .thenReturn(List.of(i1, i2));

    List<InscricaoResponseDTO> resp = service.listarConfirmadasPorEvento(eventoId, 0, 10);

    assertThat(resp).hasSize(2);
    assertThat(resp).allSatisfy(dto -> {
      assertThat(dto.eventoId()).isEqualTo(eventoId);
      assertThat(dto.status()).isEqualTo(StatusInscricao.CONFIRMADA);
      assertThat(dto.data()).isNotNull();
    });

    // Não deve consultar outros ports
    verifyNoInteractions(eventoPort, usuarioPort);
  }

  @Test
  void listarConfirmadasPorEvento_deveRetornarVazio_quandoNaoHaRegistros() {
    Long eventoId = 2L;

    when(inscricaoRepo.findByEventoAndStatus(eventoId, StatusInscricao.CONFIRMADA, 0, 10))
        .thenReturn(List.of());

    var resp = service.listarConfirmadasPorEvento(eventoId, 0, 10);

    assertThat(resp).isEmpty();
    verifyNoInteractions(eventoPort, usuarioPort);
  }

  @Test
  void listarConfirmadasPorEvento_deveUsarPaginacaoDefault_quandoNulos() {
    Long eventoId = 3L;
    // O service deve traduzir page=null/size=null para 0/10 antes de chamar o repo (conforme sua implementação)
    when(inscricaoRepo.findByEventoAndStatus(eventoId, StatusInscricao.CONFIRMADA, 0, 10))
        .thenReturn(List.of());

    var resp = service.listarConfirmadasPorEvento(eventoId, null, null);

    assertThat(resp).isEmpty();
    verify(inscricaoRepo).findByEventoAndStatus(eventoId, StatusInscricao.CONFIRMADA, 0, 10);
    verifyNoInteractions(eventoPort, usuarioPort);
  }

}
