package be.aidji.zkp.client.signature_validator;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.security.*;
import java.util.Arrays;

public class ClientApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientApplication.class);
    public static final String MESSAGE = "myOperationToSign";

    public static void main(String[] args) throws Exception {

        // Add BouncyCastle as algorithm provider
        Security.addProvider(new BouncyCastleProvider());

        // Generate a pair of keys
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
        KeyPair keyPair = keyGen.generateKeyPair();


        PrivateKey privateKey = keyPair.getPrivate();
        LOGGER.info("Private Key original" + privateKey);



        PublicKey publicKey = keyPair.getPublic();
        byte [] publicKeyEncoded = publicKey.getEncoded();
        LOGGER.info("Public Key original: " + publicKey);

        // Generate the proof
        Proof proof = generateProof(MESSAGE, privateKey, publicKeyEncoded);
        LOGGER.info("Signature BASE64: " + Arrays.toString(proof.getSignatureEncoded()));

        // Send proof to server
        boolean isVerified = sendProofForVerification(proof);
        LOGGER.info("Signature validated: " + isVerified);
    }

    private static Proof generateProof(String message, PrivateKey privateKey, byte[] publicKeyEncoded) throws Exception {

        Signature ecdsaSign = Signature.getInstance("ECDSA", "BC");
        ecdsaSign.initSign(privateKey);
        ecdsaSign.update(message.getBytes());

        byte[] signedMessage = ecdsaSign.sign();
        return new Proof(signedMessage, publicKeyEncoded);
    }

    private static boolean sendProofForVerification(Proof proof) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/signature/verify";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, proof, Boolean.class);

        HttpStatusCode responseCode = response.getStatusCode();
        Boolean booleanResponse = response.getBody();

        return responseCode.is2xxSuccessful() && Boolean.TRUE.equals(booleanResponse);
    }

}
