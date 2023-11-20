package be.aidji.zkp.server.signature_validator.controller;


import be.aidji.zkp.server.signature_validator.domain.Proof;
import be.aidji.zkp.server.signature_validator.service.SignatureVerifierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "signature")
public class SignatureVerifierController {

    private final SignatureVerifierService signatureVerifierService;

    @Autowired
    public SignatureVerifierController(SignatureVerifierService signatureVerifierService) {
        this.signatureVerifierService = signatureVerifierService;
    }

    @PostMapping(path = "/verify")
    public ResponseEntity<Boolean> verifyProof(@RequestBody Proof proof) {
        return ResponseEntity
                .status(200)
                .body(signatureVerifierService.verifyProof(proof));
    }
}
