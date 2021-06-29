package com.pac.acentueaqui.endpoints;

import com.pac.acentueaqui.models.questions.Accentuation;
import com.pac.acentueaqui.repository.AccentuatuionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1")
public class AccentuationEndpoint {
    private final AccentuatuionRepository accentuatuionRepository;

    @Autowired
    public AccentuationEndpoint(AccentuatuionRepository accentuatuionRepository) {
        this.accentuatuionRepository = accentuatuionRepository;
    }

    @PostMapping(path = "admin/add/accentuation")
    public ResponseEntity<?> addAccentuation(@RequestBody Accentuation accentuation){
        Accentuation saved = accentuatuionRepository.save(accentuation);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @PutMapping(path = "admin/edit/accentuation")
    public ResponseEntity<?> editAccentuation(@RequestBody Accentuation accentuation){
        Accentuation saved = accentuatuionRepository.save(accentuation);
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @GetMapping(path = "admin/findall/accentuation")
    public ResponseEntity<?> findAll(){
        List<Accentuation> accentuations = accentuatuionRepository.findAll();
        return new ResponseEntity<>(accentuations, HttpStatus.OK);
    }

    @GetMapping(path = "teacher/findall/accentuation")
    public ResponseEntity<?> findAllT(){
        List<Accentuation> accentuations = accentuatuionRepository.findAll();
        return new ResponseEntity<>(accentuations, HttpStatus.OK);
    }

    @DeleteMapping(path = "admin/delete/accentuation/{code}")
    public ResponseEntity<?> delete(@PathVariable Long code){
        Accentuation accentuation = accentuatuionRepository.findByCode(code);
        accentuatuionRepository.delete(accentuation);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
