package br.com.fiap.servicos.beleza.usecase.database.servico;

public interface IAtualizarServico {

    ServicoResponseDB atualizarServico(long servicoId, ServicoRequestDB requestDB);
}
