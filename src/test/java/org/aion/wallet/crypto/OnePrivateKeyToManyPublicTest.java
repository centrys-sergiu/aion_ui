package org.aion.wallet.crypto;

import com.bushidowallet.core.bitcoin.bip32.ExtendedKey;
import org.aion.mcf.account.Keystore;
import org.junit.Assert;
import org.junit.Test;

public class OnePrivateKeyToManyPublicTest {
    @Test
    public void test() {
        String address = Keystore.create("test");
        byte[] pubKey = Keystore.getKey(address, "test").getPubKey();
        ExtendedKey e = new ExtendedKey(pubKey);
        ExtendedKey derive1 = null;
        try {
            derive1 = e.derive(1);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        Assert.assertTrue(e.getPublicHex() != derive1.getPublicHex());
        Assert.assertTrue(e.getAddress() != derive1.getAddress());
        Assert.assertTrue(e.getFingerPrint() == derive1.getFingerPrint());
    }
}
