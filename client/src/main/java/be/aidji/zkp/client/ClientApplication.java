package be.aidji.zkp.client;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.security.*;
import java.security.interfaces.ECPublicKey;

public class ClientApplication {

    public static final String PASSWORD = "myPassword";

    public static void main(String[] args) throws Exception {
        Proof proof = generateProof(PASSWORD);
        System.out.println("Proof generated: " + proof);

        boolean isVerified = sendProofForVerification(proof);
        System.out.println("Proof verification status: " + isVerified);
    }

    private static ECPublicKey getPublicKey(){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/getPublicKey";
        ResponseEntity<ECPublicKey> publicKey = restTemplate.getForEntity(url, null, ECPublicKey.class);

        return publicKey.getBody();
    }

    private static Proof generateProof(String message) throws Exception {
        Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
        KeyPair keyPair = keyGen.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
        ecdsaSign.initSign(privateKey);
        ecdsaSign.update(message.getBytes());

        byte[] signature = ecdsaSign.sign();
        return new Proof(signature, publicKey);
    }

    private static boolean sendProofForVerification(Proof proof) throws Exception {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/verify?proof=" + proof;
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        HttpStatusCode responseCode = response.getStatusCode();
        return responseCode.is2xxSuccessful();
    }
}
