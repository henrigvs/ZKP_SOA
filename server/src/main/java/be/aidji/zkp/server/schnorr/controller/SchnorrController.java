package be.aidji.zkp.server.schnorr.controller;


import be.aidji.zkp.server.schnorr.domain.SchnorrProof;
import be.aidji.zkp.server.schnorr.service.SchnorrService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping(path = "schnorr")
public class SchnorrController {

    private final SchnorrService schnorrService;

    public SchnorrController(SchnorrService schnorrService) {
        this.schnorrService = schnorrService;
    }

    @PostMapping(path = "/challenge")
    public ResponseEntity<BigInteger> generateChallenge(@RequestBody BigInteger commitment) {
        return ResponseEntity
                .status(200)
                .body(schnorrService.generateChallenge(commitment));
    }


    @PostMapping(path = "/verify")
    public ResponseEntity<Boolean> verify(@RequestBody SchnorrProof proof) {
        return ResponseEntity
                .status(200)
                .body(schnorrService.verify
                        (
                                proof.getPublicKey(),
                                proof.getProof()
                        )
                );
    }
}
