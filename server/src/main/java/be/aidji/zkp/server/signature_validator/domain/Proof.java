package be.aidji.zkp.server.signature_validator.domain;

import java.util.Arrays;

public class Proof {

    private final byte[] signatureEncoded;
    private final byte[] publicKeyEncoded;

    public Proof(byte[] signatureEncoded, byte[] publicKeyEncoded) {
        this.signatureEncoded = signatureEncoded;
        this.publicKeyEncoded = publicKeyEncoded;
    }

    public byte[] getSignatureEncoded() {
        return signatureEncoded;
    }

    public byte[] getPublicKeyEncoded() {
        return publicKeyEncoded;
    }

    @Override
    public String toString() {
        return "Proof{" +
                "signatureEncoded=" + Arrays.toString(signatureEncoded) + "\n" +
                ", publicKeyEncoded=" + Arrays.toString(publicKeyEncoded) + "\n" +
                '}';
    }
}
