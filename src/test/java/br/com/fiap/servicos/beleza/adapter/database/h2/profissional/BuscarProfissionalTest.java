package br.com.fiap.servicos.beleza.adapter.database.h2.profissional;

import br.com.fiap.servicos.beleza.adapter.database.domain.TabelaProfissional;
import br.com.fiap.servicos.beleza.adapter.database.repository.ProfissionalRepository;
import br.com.fiap.servicos.beleza.usecase.database.profissional.ProfissionalResponseDB;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BuscarProfissionalTest {

    @Mock
    private ProfissionalRepository profissionalRepository;

    @InjectMocks
    private BuscarProfissional buscarProfissional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarProfissionalNaoExistente() {
        long profissionalId = 2L;

        when(profissionalRepository.findById(profissionalId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> buscarProfissional.buscarProfissional(profissionalId));

        verify(profissionalRepository, times(1)).findById(profissionalId);
    }
}

