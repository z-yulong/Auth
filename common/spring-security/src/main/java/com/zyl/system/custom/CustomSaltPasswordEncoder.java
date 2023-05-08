package com.zyl.system.custom;

import com.zyl.common.util.SHA256;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class CustomSaltPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return SHA256.encrypt(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return SHA256.verify(rawPassword.toString(), encodedPassword);
    }
}
