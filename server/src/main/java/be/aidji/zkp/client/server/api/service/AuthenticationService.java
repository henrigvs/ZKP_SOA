package be.aidji.zkp.client.server.api.service;


import be.aidji.zkp.client.server.api.domain.Proof;
import org.springframework.stereotype.Service;

import java.security.Signature;
import java.util.Base64;

@Service
public class AuthenticationService {

    private static final String PASSWORD = "myPassword";


    public boolean verifyProof(Proof proof) throws Exception {
        Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA");
        ecdsaVerify.initVerify(proof.getPublicKey());
        ecdsaVerify.update(PASSWORD.getBytes());
        return ecdsaVerify.verify(Base64.getDecoder().decode(proof.getSignature()));
    }


}
