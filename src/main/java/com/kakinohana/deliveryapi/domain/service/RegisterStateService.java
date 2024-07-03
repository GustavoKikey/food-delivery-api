package com.kakinohana.deliveryapi.domain.service;

import com.kakinohana.deliveryapi.domain.exception.EntityInUseException;
import com.kakinohana.deliveryapi.domain.exception.StateNotFoundException;
import com.kakinohana.deliveryapi.domain.model.State;
import com.kakinohana.deliveryapi.domain.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegisterStateService {

    public static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removida";
    @Autowired
    private StateRepository stateRepository;

    @Transactional
    public State save(State state){
        return stateRepository.save(state);
    };

    @Transactional
    public void delete(Long stateId){
        try {
            stateRepository.deleteById(stateId);
        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(stateId);
        } catch (DataIntegrityViolationException e) {
            throw new EntityInUseException(
                    String.format(MSG_ESTADO_EM_USO, stateId));
        }
    }

    public State findOrFail(Long stateId){
        return stateRepository.findById(stateId)
                .orElseThrow(() -> new StateNotFoundException(stateId));
    }

}
