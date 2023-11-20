package be.aidji.zkp.server.schnorr.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;

@Service
public class SchnorrService {

    private static final BigInteger G = BigInteger.valueOf(2);
    private static final BigInteger P = BigInteger.valueOf(821);

    private BigInteger commitment;
    private BigInteger challenge;

    public BigInteger generateChallenge(BigInteger commitment){
        this.commitment = commitment;
        this.challenge = new BigInteger(256, new SecureRandom());
        return this.challenge;
    }


    public boolean verify(BigInteger publicKey, BigInteger proof){
        BigInteger leftHandSide = G.modPow(proof, P);
        BigInteger rightHandSide = commitment
                .multiply(publicKey.modPow(challenge, P))
                .mod(P);
        return leftHandSide.equals(rightHandSide);
    }
}
