// package com.eventosapi.comunicacoes.service;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyLong;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.util.Optional;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;
// import org.mockito.Captor;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.springframework.mail.javamail.JavaMailSender;

// import com.eventosapi.comunicacoes.application.port.UsuarioClientPort;
// import com.eventosapi.comunicacoes.application.services.EmailService;
// import com.eventosapi.comunicacoes.application.services.PDFService;
// import com.eventosapi.comunicacoes.domain.model.Usuario;
// import com.eventosapi.comunicacoes.interfaces.dto.InscricaoDTO;

// import jakarta.mail.internet.MimeMessage;

// public class EmailServiceTest {

//     @Mock
//     private JavaMailSender mailSender;

//     @Mock
//     private PDFService pdfService;

//     @Mock
//     private UsuarioClientPort usuarioClient;

//     @InjectMocks
//     private EmailService emailService;

//     @Captor
//     private ArgumentCaptor<MimeMessage> mimeMessageCaptor;

//     @BeforeEach
//     void setUp() {
//         MockitoAnnotations.openMocks(this);
//     }

//     @Test
//     void deveEnviarEmailComAnexo() throws Exception {
//         byte[] pdfBytes = "pdf fake".getBytes();
//         when(pdfService.geraRelatorioPDF(any())).thenReturn(pdfBytes);

//         Usuario usuario = new Usuario();
//         usuario.setEmail("teste@dominio.com");
//         when(usuarioClient.findById(anyLong())).thenReturn(Optional.of(usuario));

//         MimeMessage mimeMessage = mock(MimeMessage.class);
//         when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

//         InscricaoDTO inscricaoDTO = new InscricaoDTO();
//         inscricaoDTO.setIdUsuario(1L);

//         emailService.enviarComAnexo(inscricaoDTO);

//         verify(mailSender, times(1)).createMimeMessage();
//         verify(mailSender, times(1)).send(mimeMessage);
//         verify(pdfService, times(1)).geraRelatorioPDF(inscricaoDTO);
//         verify(usuarioClient, times(1)).findById(1L);
//     }
// }
