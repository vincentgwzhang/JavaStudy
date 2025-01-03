package jdk21;

import javax.crypto.DecapsulateException;
import javax.crypto.KEM;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class EncryptStudy {

    public void main() throws NoSuchAlgorithmException, InvalidKeyException, DecapsulateException {

        final var keyPairGenerator = KeyPairGenerator.getInstance("X25519");
        final var keyPair = keyPairGenerator.generateKeyPair();
        final var publicKey = keyPair.getPublic();
        final var privateKey = keyPair.getPrivate();

        final var sendersKem = KEM.getInstance("DHKEM");
        final var sender = sendersKem.newEncapsulator(publicKey);
        final var encapsulated = sender.encapsulate();
        final var secretKey = encapsulated.key();

        final var receiversKem = KEM.getInstance("DHKEM");
        final var receiver = receiversKem.newDecapsulator(privateKey);
        final var receivedSecretKey = receiver.decapsulate(encapsulated.encapsulation());

        if (Arrays.equals(secretKey.getEncoded(), receivedSecretKey.getEncoded())) {
            System.out.println(secretKey.getEncoded());
            System.out.println("Keys match!");
        } else {
            System.out.println("Keys don't match!");
        }

    }
}
