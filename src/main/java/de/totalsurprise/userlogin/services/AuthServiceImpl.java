package de.totalsurprise.userlogin.services;

import de.totalsurprise.userlogin.persistence.UserDAO;
import de.totalsurprise.userlogin.persistence.UserEntity;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

@Stateless
public class AuthServiceImpl implements AuthService {

    @Inject
    UserDAO dao;

    /**
     * Check if the entered credentials are valid.
     * @param enteredEmailAdress
     * @param enteredPassword
     * @return true, if the entered credentials are valid.
     */
    public boolean validate(String enteredEmailAdress, String enteredPassword) {
        UserEntity userEntity = dao.findUserByMailadresse(enteredEmailAdress);

        if (userEntity == null) return false;
        else {
            boolean passwordIsCorrect = checkPassword(enteredPassword, userEntity);
            return passwordIsCorrect;
        }
    }

    /**
     * Check if the entered password matches the UserEntity from database.
     */
    private boolean checkPassword(String enteredPasswordPlainText, UserEntity userEntity) {
        byte[] storedPassword = userEntity.getEncryptedPassword();
        byte[] enteredPasswordEncrypted = encrypt(enteredPasswordPlainText.toCharArray(), userEntity.getSalt());

        return Arrays.equals(storedPassword, enteredPasswordEncrypted);
    }

    /**
     * Salt the given password and encrypt with PBKDF2WithHmacSHA512 algorithm.
     */
    public byte[] encrypt(char[] passwordPlainText, byte[] salt) {
        PBEKeySpec spec = new PBEKeySpec(passwordPlainText, salt, 1000, 512);

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
