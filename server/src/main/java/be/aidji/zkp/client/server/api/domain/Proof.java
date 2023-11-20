package be.aidji.zkp.client.server.api.domain;

import java.security.PublicKey;

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
