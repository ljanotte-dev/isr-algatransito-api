package com.algaworks.algatransito.domain.service;

import com.algaworks.algatransito.domain.exception.NegocioException;
import com.algaworks.algatransito.domain.model.StatusVeiculo;
import com.algaworks.algatransito.domain.model.Veiculo;
import com.algaworks.algatransito.domain.repository.VeiculoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class RegistroVeiculoService {

    private VeiculoRepository veiculoRepository;

    @Transactional
    public Veiculo cadastrar(Veiculo novoVeiculo) {

        if (novoVeiculo.getId() != null) {
            throw new NegocioException("Veiculo a ser cadastrado não deve possuir um id");
        }

        boolean placaEmUso = veiculoRepository.findByPlaca(novoVeiculo.getPlaca())
                .filter(veiculo -> veiculo.equals(novoVeiculo))
                .isPresent();

        if (placaEmUso) {
            throw new NegocioException("Já existe um veiculo cadastrada com esta placa");
        }

        novoVeiculo.setStatus(StatusVeiculo.RECULAR);
        novoVeiculo.setDataCadastro(LocalDateTime.now());

        return veiculoRepository.save(novoVeiculo);
    }
}
