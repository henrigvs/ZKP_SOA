package be.aidji.zkp.client.server.api.service;


import be.aidji.zkp.client.server.api.domain.Proof;
import be.aidji.zkp.client.server.api.infrastructure.exception.SignatureVerifierException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

@Service
public class SignatureVerifierService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignatureVerifierService.class);

    public boolean verifyProof(Proof proof) {

        try {
            // Add BouncyCastle as algorithm provider
            Security.addProvider(new BouncyCastleProvider());

            LOGGER.info("Public key encoded: " + Arrays.toString(proof.getPublicKeyEncoded()));
            PublicKey publicKey = decodeArrayOfBytes(proof.getPublicKeyEncoded());
            LOGGER.info("Public key decoded: " + publicKey.toString());


            byte [] signedMessage = proof.getSignatureEncoded();

            Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
            ecdsaVerify.initVerify(publicKey);
            ecdsaVerify.update("myOperationToSign".getBytes());

            return ecdsaVerify.verify(signedMessage);
        } catch (Exception e){
            LOGGER.info(e.getMessage());
            throw new SignatureVerifierException(e.getMessage());
        }
    }

    private PublicKey decodeArrayOfBytes(byte[] bytes) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytes);

        return keyFactory.generatePublic(publicKeySpec);
    }
}
