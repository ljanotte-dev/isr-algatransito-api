package com.algaworks.algatransito.api.controller;

import com.algaworks.algatransito.domain.exception.NegocioException;
import com.algaworks.algatransito.domain.model.Proprietario;
import com.algaworks.algatransito.domain.repository.ProprietarioRepository;
import com.algaworks.algatransito.domain.service.RegistroProprietarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/proprietarios")
public class ProprietarioController {

//    @Autowired
    private final RegistroProprietarioService registroProprietarioService;
    private final ProprietarioRepository proprietarioRepository;


    //    ====
//    @PersistenceContext
//    private EntityManager manager;

    @GetMapping
    public List<Proprietario> listar(){
//        var propprietario1 = new Proprietario();
//        propprietario1.setId(1L);
//        propprietario1.setNome("João");
//        propprietario1.setTelefone("34 99999-1111");
//        propprietario1.setEmail("joaodascouves@algaworks.com");
//
//        var propprietario2 = new Proprietario();
//        propprietario2.setId(2L);
//        propprietario2.setNome("Maria");
//        propprietario2.setTelefone("34 99999-2222");
//        propprietario2.setEmail("mariadascouves@algaworks.com");
//
//        return Arrays.asList(propprietario1, propprietario2);

//        TypedQuery<Proprietario> query = manager.createQuery("from Proprietario", Proprietario.class);
//        return  query.getResultList();
//        ====
//        return manager.createQuery("from Proprietario", Proprietario.class ).getResultList();

        return proprietarioRepository.findAll();

    }

    @GetMapping("/nome")
    public List<Proprietario> listarNome(){

//        return proprietarioRepository.findByNome("João das Couves");
        return proprietarioRepository.findByNomeContaining("a");

    }

    @GetMapping("/{proprietarioId}")
    public ResponseEntity<Proprietario> buscar(@PathVariable Long proprietarioId){
//        Optional<Proprietario> proprietario = proprietarioRepository.findById(proprietarioId);
//        if (proprietario.isPresent()) {
//            return ResponseEntity.ok(proprietario.get());
//        }
//        return ResponseEntity.notFound().build();

         return proprietarioRepository.findById(proprietarioId)
                 .map(ResponseEntity::ok)
                 .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Proprietario adicionar(@Valid @RequestBody Proprietario proprietario){
        return registroProprietarioService.salvar(proprietario);
//        return proprietarioRepository.save(proprietario);
    }

    @PutMapping("/{proprietarioId}")
    public  ResponseEntity<Proprietario> atualizar(@PathVariable Long proprietarioId,
                                                   @Valid @RequestBody Proprietario proprietario){
        if (!proprietarioRepository.existsById(proprietarioId)){
            return ResponseEntity.notFound().build();
        }

        proprietario.setId(proprietarioId);
        Proprietario proprietarioAtualizado = registroProprietarioService.salvar(proprietario);

        return ResponseEntity.ok(proprietario);

    }

    @DeleteMapping("/{proprietarioId}")
    public ResponseEntity<Void> remover(@PathVariable Long proprietarioId) {
        if (!proprietarioRepository.existsById(proprietarioId)) {
            return ResponseEntity.notFound().build();
        }

        registroProprietarioService.excluir((proprietarioId));
//        proprietarioRepository.deleteById(proprietarioId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capturar(NegocioException e) {
        return  ResponseEntity.badRequest().body(e.getMessage());
    }
}
