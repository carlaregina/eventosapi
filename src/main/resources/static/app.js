const API = '/api';
const TIPOS_EVENTO = [
  'TEATRO',
  'PASSEIOS_TOURS',
  'ESPORTE',
  'PALESTRA',
  'FESTA_SHOW',
  'CURSO'
];
function tipoEventoLabel(v){
  const map = { PASSEIOS_TOURS: 'Passeios & Tours', FESTA_SHOW: 'Festa/Show' };
  return map[v] || v.charAt(0) + v.slice(1).toLowerCase();
}

const els = {
  // steps
  step1: document.getElementById('step1'),
  step2: document.getElementById('step2'),
  step3: document.getElementById('step3'),
  step4: document.getElementById('step4'),
  toast: document.getElementById('toast'),

  // step1 - usuário
  uNome: document.getElementById('uNome'),
  uEmail: document.getElementById('uEmail'),
  uTelefone: document.getElementById('uTelefone'),
  uTipo: document.getElementById('uTipo'),
  btnCriarUsuario: document.getElementById('btnCriarUsuario'),
  usuarioCriado: document.getElementById('usuarioCriado'),
  btnGoStep2: document.getElementById('btnGoStep2'),

  // step2 - local
  lNome: document.getElementById('lNome'),
  lCep: document.getElementById('lCep'),
  lLogradouro: document.getElementById('lLogradouro'),
  lNumero: document.getElementById('lNumero'),
  lBairro: document.getElementById('lBairro'),
  lCidade: document.getElementById('lCidade'),
  lEstado: document.getElementById('lEstado'),
  lTipo: document.getElementById('lTipo'),
  btnCriarLocal: document.getElementById('btnCriarLocal'),
  localCriado: document.getElementById('localCriado'),
  btnBack1: document.getElementById('btnBack1'),
  btnGoStep3: document.getElementById('btnGoStep3'),

  // step3 - evento
  eTitulo: document.getElementById('eTitulo'),
  eDescricao: document.getElementById('eDescricao'),
  eData: document.getElementById('eData'),
  eTipo: document.getElementById('eTipo'),
  eMax: document.getElementById('eMax'),
  eOrganizadorId: document.getElementById('eOrganizadorId'),
  eLocalId: document.getElementById('eLocalId'),
  btnCriarEvento: document.getElementById('btnCriarEvento'),
  btnBack2a: document.getElementById('btnBack2a'),
  btnGoStep4From3: document.getElementById('btnGoStep4From3'),

  // step4 - inscrição
  iUsuarioId: document.getElementById('iUsuarioId'),
  iEventoId: document.getElementById('iEventoId'),
  btnInscrever: document.getElementById('btnInscrever'),
  btnBack3: document.getElementById('btnBack3'),

  // listagem
  listaEventos: document.getElementById('listaEventos'),
};

// estado
let usuarioIdCriado = null;
let localIdCriado = null;
let eventoIdCriado = null;

// init
bind();
initLoad();

function bind(){
  // navegação
  els.btnGoStep2.addEventListener('click', () => goto(2));
  els.btnBack1.addEventListener('click', () => goto(1));

  // ⚠️ depois do LOCAL, vamos ao EVENTO (passo 3)
  els.btnGoStep3.addEventListener('click', () => goto(3));
  els.btnBack2a.addEventListener('click', () => goto(2));

  els.btnGoStep4From3.addEventListener('click', () => goto(4));
  els.btnBack3.addEventListener('click', () => goto(3));

  // ações
  els.btnCriarUsuario.addEventListener('click', criarUsuario);
  els.btnCriarLocal.addEventListener('click', criarLocal);
  els.btnCriarEvento.addEventListener('click', criarEvento);
  els.btnInscrever.addEventListener('click', inscrever);
}

function goto(n){
  [els.step1, els.step2, els.step3, els.step4].forEach(s => s.classList.add('hidden'));
  if(n===1) els.step1.classList.remove('hidden');
  if(n===2) els.step2.classList.remove('hidden');
  if(n===3) els.step3.classList.remove('hidden');
  if(n===4) els.step4.classList.remove('hidden');
}

async function initLoad(){
  // Carrega dados necessários. Os loaders de enums são opcionais (toleram 404).
  await Promise.all([
    carregarTiposUsuario(),   // opcional
    carregarEstados(),        // opcional
    carregarTiposLocal(),     // opcional
    carregarTiposEvento(),    // recomendado
    carregarLocaisParaSelect(), // recomendado p/ criar evento
    carregarEventos(0, 20, 'data,asc'), // lista inicial para inscrição
  ]);
}

/* ==============================
   Usuário
   ============================== */
async function criarUsuario(){
  const payload = {
    nome: (els.uNome.value || '').trim(),
    email: (els.uEmail.value || '').trim(),
    telefone: (els.uTelefone.value || '').trim(),
    tipo: els.uTipo.value || null
  };
  if(!payload.nome || !payload.email || !payload.telefone){
    return toast('Preencha nome, e-mail e telefone.');
  }
  try{
    const r = await fetch(`${API}/usuarios`, {
      method:'POST',
      headers:{'Content-Type':'application/json'},
      body: JSON.stringify(payload)
    });
    if(!r.ok) throw await problemAsError(r);
    const u = await r.json();
    afterUsuarioCriado(u);
    toast('Usuário criado com sucesso.');
  }catch(e){ toast(msg(e)); }
}

function afterUsuarioCriado(u){
  usuarioIdCriado = u.id;
  els.usuarioCriado.textContent = `ID ${u.id} – ${u.nome} (${u.email})`;
  els.iUsuarioId.value = u.id;       // para inscrição
  els.eOrganizadorId.value = u.id;   // organizador do evento
  els.btnGoStep2.disabled = false;
}

/* ==============================
   Local
   ============================== */
async function criarLocal(){
  const payload = {
    nome: (els.lNome.value || '').trim(),
    cep: (els.lCep.value || '').trim(),
    logradouro: (els.lLogradouro.value || '').trim(),
    numero: (els.lNumero.value || '').trim(),
    bairro: (els.lBairro.value || '').trim(),
    cidade: (els.lCidade.value || '').trim(),
    estado: els.lEstado.value || null,
    tipo: els.lTipo.value || null
  };
  if(!payload.nome || !payload.cep || !payload.logradouro || !payload.numero ||
     !payload.bairro || !payload.cidade || !payload.estado || !payload.tipo){
    return toast('Preencha todos os campos obrigatórios do local.');
  }
  try{
    const r = await fetch(`${API}/locais`, {
      method:'POST',
      headers:{'Content-Type':'application/json'},
      body: JSON.stringify(payload)
    });
    if(!r.ok) throw await problemAsError(r);
    const l = await r.json();
    localIdCriado = l.id;
    els.localCriado.textContent = `ID ${l.id} – ${l.nome} (${l.cidade}/${l.estado})`;

    // habilita o botão correto: Ir para 3) Criar evento
    els.btnGoStep3.disabled = false;

    // atualizar select de locais do evento
    await carregarLocaisParaSelect();
    if (localIdCriado) els.eLocalId.value = String(localIdCriado);

    toast('Local criado com sucesso.');
  }catch(e){ toast(msg(e)); }
}

/* ==============================
   Evento
   ============================== */
async function criarEvento(){
  const titulo = (els.eTitulo.value || '').trim();
  const dataLocal = els.eData.value;        // "YYYY-MM-DDTHH:mm"
  const tipo = els.eTipo.value; // sem "|| OUTROS"
  if (!TIPOS_EVENTO.includes(tipo)) {
    return toast('Selecione um tipo de evento válido.');
  }         // ⚠️ sem fallback
  const max = Number(els.eMax.value);
  const organizadorId = Number(els.eOrganizadorId.value);
  const localId = Number(els.eLocalId.value);

  // valida campos básicos
  if(!titulo || !dataLocal || !tipo || !max || !organizadorId || !localId){
    return toast('Preencha título, data, tipo, máx. participantes, organizador e local.');
  }

  // ⚠️ valida o tipo contra a lista oficial (impede "OUTROS")
  if (!TIPOS_EVENTO.includes(tipo)) {
    return toast('Selecione um tipo de evento válido.');
  }

  const payload = {
    titulo,
    descricao: (els.eDescricao.value || '').trim() || null,
    data: toLocalDateTimeISO(dataLocal), // "YYYY-MM-DDTHH:mm:ss"
    tipo,                                 // garantido: um dos TIPOS_EVENTO
    maxParticipantes: max,
    organizadorId,
    localId
  };

  // debug – veja no DevTools > Network > Request Payload
  console.log('DEBUG payload evento:', payload);

  try{
    const r = await fetch(`${API}/eventos`, {
      method:'POST',
      headers:{'Content-Type':'application/json'},
      body: JSON.stringify(payload)
    });
    if(!r.ok) throw await problemAsError(r);

    const ev = await r.json();
    eventoIdCriado = ev.id;

    // Recarrega a lista e pré-seleciona o evento recém-criado
    await carregarEventos(0, 20, 'data,asc');
    if (eventoIdCriado) els.iEventoId.value = String(eventoIdCriado);

    // Vai para INSCRIÇÃO
    goto(4);
    toast('Evento criado! Você já pode concluir a inscrição.');
  }catch(e){ toast(msg(e)); }
}

/* ==============================
   Inscrição
   ============================== */
   function getEventId(e){
  // tenta nomes comuns que já vi em DTOs
  return e?.id ?? e?.eventoId ?? e?.idEvento ?? e?.id_evento ?? e?.eventId ?? null;
}
async function inscrever(){



  
  const idUsuario = Number(els.iUsuarioId.value);
  const idEvento  = Number(els.iEventoId.value);
  if(!idUsuario || !idEvento) return toast('Informe o usuário (ID) e selecione um evento.');
  try{
    const r = await fetch(`${API}/inscricoes`, {
      method:'POST',
      headers:{'Content-Type':'application/json'},
      body: JSON.stringify({ idUsuario, idEvento })
    });
    if(!r.ok) throw await problemAsError(r);
    await r.json();
    toast('Inscrição criada! Verifique o e-mail para o voucher.');
  }catch(e){ toast(msg(e)); }
}

/* ==============================
   Carregadores / Helpers
   ============================== */

// Eventos (Page<EventoResponseDTO>)
async function carregarEventos(page=0, size=20, sort='data,asc'){
  try{
    const r = await fetch(`${API}/eventos?page=${page}&size=${size}&sort=${encodeURIComponent(sort)}`);
    if(!r.ok) throw new Error('Não foi possível listar eventos.');
    const body = await r.json();
    const eventos = Array.isArray(body) ? body : (body.content || []);
    console.log('[carregarEventos] body recebido:', body);
    popularEventos(eventos);
  }catch(e){ toast(msg(e)); }
}

function popularEventos(eventos){
  // select de inscrição
    console.log('[popularEventos] recebidos:', eventos.length, 'itens');
  els.iEventoId.innerHTML = '';
  if(!Array.isArray(eventos) || eventos.length===0){
    const op = document.createElement('option');
    op.value = '';
    op.textContent = 'Sem eventos cadastrados';
    els.iEventoId.appendChild(op);

      if (id == null) {
        console.warn('[popularEventos] ignorando item sem ID:', e);
        return;

      }

       const opts = [...els.iEventoId.options].map(o => o.outerHTML);
  console.log('[popularEventos] options geradas:', opts);
  } else {
    const op0 = document.createElement('option');
    op0.value = '';
    op0.textContent = 'Selecione um evento';
    els.iEventoId.appendChild(op0);

    eventos.forEach(e => {
      const op = document.createElement('option');
      op.value = e.id;
      op.textContent = `${e.titulo} — ${formatDate(e.data)}`;
      els.iEventoId.appendChild(op);
    });
  }

  // tabela
  els.listaEventos.innerHTML = '';
  (eventos || []).forEach(e => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${esc(e.titulo)}</td>
      <td>${formatDate(e.data)}</td>
      <td>${esc(e.tipo || '')}</td>
      <td>${esc(e.localNome || '')}</td>
    `;
    els.listaEventos.appendChild(tr);
  });
}

// Locais para select do evento
async function carregarLocaisParaSelect(){
  try{
    const r = await fetch(`/api/locais?size=1000&sort=${encodeURIComponent('nome,asc')}`);
    if (!r.ok) throw await problemAsError(r);

    const body = await r.json();
    const locais = Array.isArray(body) ? body : (Array.isArray(body.content) ? body.content : []);

    const sel = els.eLocalId;
    sel.innerHTML = '<option value="">Selecione um local</option>';

    if (!locais.length) {
      const op = document.createElement('option');
      op.value = '';
      op.textContent = 'Nenhum local cadastrado';
      sel.appendChild(op);
      return;
    }

    locais.forEach(l => {
      const op = document.createElement('option');
      op.value = l.id; // seu entity Local tem 'id' (id_local no banco) -> serializado como 'id'
      const cidade = l.cidade ?? '';
      const estado = l.estado ?? ''; // enum vai em string: "SP", "RJ", ...
      op.textContent = `${l.nome}${cidade && estado ? ` — ${cidade}/${estado}` : ''}`;
      sel.appendChild(op);
    });
  } catch (e) {
    toast(msg(e));
  }
}

// Enums (opcionais; silenciam se endpoint não existir)
async function carregarTiposUsuario(){
  try{
    const r = await fetch(`${API}/enums/tipos-usuario`);
    if(!r.ok) return;
    const tipos = await r.json();
    els.uTipo.innerHTML = '<option value="">Selecione</option>';
    tipos.forEach(t => {
      const op = document.createElement('option');
      op.value = t;
      op.textContent = t;
      els.uTipo.appendChild(op);
    });
  }catch{}
}
async function carregarTiposLocal(){
  try{
    const r = await fetch(`${API}/enums/tipos-local`);
    if(!r.ok) return;
    const tipos = await r.json();
    els.lTipo.innerHTML = '<option value="">Selecione</option>';
    tipos.forEach(t => {
      const op = document.createElement('option');
      op.value = t;
      op.textContent = t.charAt(0) + t.slice(1).toLowerCase();
      els.lTipo.appendChild(op);
    });
  }catch{}
}
async function carregarEstados(){
  try{
    const r = await fetch(`${API}/enums/estados`);
    if(!r.ok) return;
    const estados = await r.json();
    els.lEstado.innerHTML = '<option value="">UF</option>';
    estados.forEach(uf => {
      const op = document.createElement('option');
      op.value = uf;
      op.textContent = uf;
      els.lEstado.appendChild(op);
    });
  }catch{}
}
async function carregarTiposEvento(){
  const sel = els.eTipo;

  // placeholder padrão
  sel.innerHTML = '<option value="" selected disabled>Selecione</option>';

  let list = null;
  try{
    const r = await fetch(`${API}/enums/tipos-evento`); // se existir
    if (r.ok) {
      const arr = await r.json(); // ex.: ["TEATRO","PASSEIOS_TOURS",...]
      // aceite SOMENTE o que seu Enum de fato mapeia
      list = arr.filter(v => TIPOS_EVENTO.includes(v));
    }
  }catch(_){ /* ignora e cai no fallback seguro */ }

  if (!list || list.length === 0) {
    // fallback seguro/alinhado ao Enum do back
    list = TIPOS_EVENTO.slice();
  }

  list.forEach(v => {
    const op = document.createElement('option');
    op.value = v;
    op.textContent = tipoEventoLabel(v);
    sel.appendChild(op);
  });

  // Higiene: remova qualquer opção "OUTROS" que porventura alguém tenha deixado no HTML
  const optOutros = sel.querySelector('option[value="OUTROS"]');
  if (optOutros) optOutros.remove();
}


/* ==============================
   Utilidades
   ============================== */

function toast(text){
  els.toast.textContent = text;
  els.toast.classList.remove('hidden');
  setTimeout(()=> els.toast.classList.add('hidden'), 3200);
}

function msg(e){ return (e && e.message) ? e.message : 'Erro inesperado.'; }

/** Tenta extrair {message, errors[]} de respostas problem+json/validation */
async function problemAsError(resp){
  const status = resp.status;
  let text = '';
  try{ text = await resp.text(); } catch {}
  try{
    const json = JSON.parse(text);
    if (json.message) return new Error(json.message);
    if (Array.isArray(json.errors) && json.errors.length) {
      return new Error(json.errors.map(e => e.defaultMessage || e.message || e).join('; '));
    }
  }catch{}
  return new Error(text || `HTTP ${status}`);
}

function esc(s){ return String(s ?? '').replace(/[&<>"'/]/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;','/':'&#x2F;'}[c])); }

function formatDate(iso){
  if(!iso) return '';
  const d = new Date(iso);
  if(Number.isNaN(d.getTime())) return iso;
  return d.toLocaleString('pt-BR');
}

/**
 * Converte o valor do <input type="datetime-local"> (ex.: "2025-10-05T19:30")
 * para "YYYY-MM-DDTHH:mm:ss" (compatível com LocalDateTime).
 */
function toLocalDateTimeISO(localValue){
  if(!localValue) return null;
  return localValue.length === 16 ? localValue + ':00' : localValue;
}
