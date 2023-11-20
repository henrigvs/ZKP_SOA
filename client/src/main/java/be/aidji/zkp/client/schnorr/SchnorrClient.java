package be.aidji.zkp.client.schnorr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.security.SecureRandom;

public class SchnorrClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchnorrClient.class);
    private static final BigInteger G = BigInteger.valueOf(2);
    private static final BigInteger P = BigInteger.valueOf(821);
    private static final SecureRandom RANDOM = new SecureRandom();

    public static void main(String[] args) {

        // Generate the private key and compute a public key
        BigInteger privateKey = new BigInteger("E9873D79C6D87DC0FB6A5778633389F4453213303DA61F20BD67FC233", 16);
        BigInteger publicKey = G.modPow(privateKey, P);

        //privateKey = new BigInteger("E9873D79C6D87DC0FB6A5778633389F4453213303DA61F20BD67FC234", 16);

        // Generate the commitment
        BigInteger r = new BigInteger(256, RANDOM);
        BigInteger commitment = G.modPow(r, P);

        // Send commitment and retrieve challenge from server
        BigInteger challenge = sendCommitment(commitment);
        BigInteger proof = r.add(challenge.multiply(privateKey)).mod(P.subtract(BigInteger.ONE));

        // Compute the proof and send to the server with the public key
        SchnorrProof schnorrProof = new SchnorrProof
                (
                        publicKey,
                        proof
                );

        // Get response from server
        boolean isVerified = sendForVerification(schnorrProof);
        LOGGER.info("Proof verified: " + isVerified);
    }

    private static BigInteger sendCommitment(BigInteger commitment){
        String requestChallengeUrl = "http://localhost:8080/schnorr/challenge";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<BigInteger> challenge = restTemplate.postForEntity(requestChallengeUrl, commitment, BigInteger.class);

        LOGGER.info("Request challenge status code: " + challenge.getStatusCode());

        return challenge.getBody();
    }

    private static boolean sendForVerification(SchnorrProof proof){
        String verifyUrl = "http://localhost:8080/schnorr/verify";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Boolean> isVerified = restTemplate.postForEntity(verifyUrl, proof, Boolean.class);

        LOGGER.info("Request verification status code: " + isVerified.getStatusCode());

        return Boolean.TRUE.equals(isVerified.getBody());
    }
}
