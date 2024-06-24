package com.kakinohana.deliveryapi.api.controller;

import com.kakinohana.deliveryapi.domain.model.State;
import com.kakinohana.deliveryapi.domain.repository.StateRepository;
import com.kakinohana.deliveryapi.domain.service.RegisterStateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private RegisterStateService registerStateService;

    @GetMapping
    public List<State> list(){
        return stateRepository.findAll();
    }

    @GetMapping("/{stateId}")
    public State find(@PathVariable("stateId") Long stateId){
       return registerStateService.findOrFail(stateId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<State> add(@RequestBody @Valid State state){
        registerStateService.save(state);
        return ResponseEntity.status(HttpStatus.CREATED).body(state);
    }

    @PutMapping("/{stateId}")
    public State update(@PathVariable Long stateId,
                        @RequestBody @Valid State state) {
            State stateAtual = registerStateService.findOrFail(stateId);

            BeanUtils.copyProperties(state, stateAtual, "id");

            return registerStateService.save(stateAtual);
    }

    @DeleteMapping("/{stateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long stateId) {
        registerStateService.delete(stateId);
    }



}
