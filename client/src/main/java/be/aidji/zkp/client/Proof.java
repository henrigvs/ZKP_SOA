package be.aidji.zkp.client;

import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;

public class Proof {

    private final byte[] signature;
    private final PublicKey publicKey;

    public Proof(byte[] signature, PublicKey publicKey) {
        this.signature = signature;
        this.publicKey = publicKey;
    }

    public byte[] getSignature() {
        return signature;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
