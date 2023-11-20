package be.aidji.zkp.client.schnorr;

import java.math.BigInteger;

public class SchnorrProof {

    private final BigInteger publicKey;
    private final BigInteger proof;

    public SchnorrProof(BigInteger publicKey, BigInteger proof) {
        this.publicKey = publicKey;
        this.proof = proof;
    }


    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getProof() {
        return proof;
    }


}
