package com.smart.config;

import com.smart.utils.CodecUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncode  implements PasswordEncoder{
    @Override
    public String encode(CharSequence charSequence) {

        return CodecUtils.passwordBcryptEncode(charSequence.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodePassword) {
        return CodecUtils.passwordBcryptDecode(rawPassword.toString(),encodePassword);
    }

}
