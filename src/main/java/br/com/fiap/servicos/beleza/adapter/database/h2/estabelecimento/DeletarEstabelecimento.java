package br.com.fiap.servicos.beleza.adapter.database.h2.estabelecimento;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaEstabelecimento;
import br.com.fiap.servicos.beleza.adapter.database.exceptions.NotFoundException;
import br.com.fiap.servicos.beleza.adapter.database.repository.EstabelecimentoRepository;
import br.com.fiap.servicos.beleza.usecase.database.estabelecimento.IDeletarEstabelecimento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeletarEstabelecimento implements IDeletarEstabelecimento {

    private final EstabelecimentoRepository estabelecimentoRepository;

    @Override
    @Transactional
    public void deletarEstabelecimento(long estabelecimentoId) {
        var estabelecimento = estabelecimentoRepository.findById(estabelecimentoId);
        var estabelecimentoValidado = validarEstabelecimento(estabelecimento, estabelecimentoId);
        estabelecimentoRepository.delete(estabelecimentoValidado.get());
    }

    private Optional<TabelaEstabelecimento> validarEstabelecimento(Optional<TabelaEstabelecimento> response, long casaId) {
        if (!response.isPresent())
            throw new NotFoundException("Recurso nao encontrado na base");
        return response;
    }
}