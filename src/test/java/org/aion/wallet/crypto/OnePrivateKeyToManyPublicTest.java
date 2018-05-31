package org.aion.wallet.crypto;

import com.bushidowallet.core.bitcoin.bip32.Derivation;
import com.bushidowallet.core.bitcoin.bip32.ExtendedKey;
import org.aion.crypto.ECKey;
import org.aion.crypto.ECKeyFac;
import org.aion.crypto.ed25519.ECKeyEd25519;
import org.aion.mcf.account.Keystore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.security.KeyStore;
import java.security.Security;

public class OnePrivateKeyToManyPublicTest {
    String alreadyCreatedAddres = "0xa091aec6e5bc4ddc9cc599731944519c96f47643432cd0b5b226dbabcbbca582";
    private ExtendedKey rootKey = null;
    private ECKey oneKey = null;

    @Before
    public void setUp() {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        String address = null;
        if(!Keystore.exist(alreadyCreatedAddres)) {
            address = Keystore.create("test");
        }
        else {
            address = alreadyCreatedAddres;
        }

        oneKey = Keystore.getKey(address, "test");
        byte[] privKey = oneKey.getPrivKeyBytes();
        rootKey = new ExtendedKey(privKey);
    }

    @Test
    public void testDerivations() {
//        Derivation derivation = new Derivation(rootKey);
//
//        ExtendedKey derive0 = null;
//        try {
//            derive0 = derivation.basic(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Assert.assertNotNull(derive0);
//        Assert.assertNotEquals(rootKey.getAddress().toString(), derive0.getAddress().toString());
//
//        ExtendedKey derive1 = null;
//        try {
//            derive1 = derivation.basic(1);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        Assert.assertTrue(rootKey.getPublicHex() != derive1.getPublicHex());
//        Assert.assertTrue(!rootKey.getAddress().toString().equals(derive1.getAddress().toString()));
//
//        try {
//            derivation.accountMaster(0, 1);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testMultiplePublicKeysFromPrivateKey() {
        ECKey firstGen = oneKey.fromPrivate(oneKey.getPrivKeyBytes());
        ECKey secondGen = oneKey.fromPrivate(oneKey.getPrivKeyBytes());

        Assert.assertEquals(firstGen.getPrivKeyBytes(), oneKey.getPrivKeyBytes());
        Assert.assertNotEquals(firstGen.getPubKey(), oneKey.getPubKey());

        Assert.assertEquals(secondGen.getPrivKeyBytes(), oneKey.getPrivKeyBytes());
        Assert.assertNotEquals(secondGen.getPubKey(), oneKey.getPubKey());
    }
}
