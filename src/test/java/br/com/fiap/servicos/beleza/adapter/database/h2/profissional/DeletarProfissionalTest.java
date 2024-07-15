package br.com.fiap.servicos.beleza.adapter.database.h2.profissional;

import br.com.fiap.servicos.beleza.adapter.database.repository.ProfissionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DeletarProfissionalTest {

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private DeletarProfissional deletarProfissional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deletarProfissional() {
        long profissionalId = 1L;

        deletarProfissional.deletarProfissional(profissionalId);

        verify(profissionalRepository, times(1)).deleteById(profissionalId);
    }
}
