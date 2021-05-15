package com.degree.back.posture.corrector.service;

import com.degree.back.posture.corrector.repository.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;

public class JwtService {

  private static final byte[] key = TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=");

  static String create(UserEntity userEntity) {
    var currentDate = new Date();
    var expirationDate = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(expirationDate);
    cal.add(Calendar.DATE, 1);
    expirationDate = cal.getTime();

    return Jwts.builder()
        .setIssuer("BpcService")
        .setSubject("authenticated")
        .claim("id", userEntity.getId())
        .claim("firstName", userEntity.getFirstName())
        .claim("lastName", userEntity.getLastName())
        .claim("email", userEntity.getEmail())
        .claim("age", userEntity.getAge())
        .setIssuedAt(currentDate)
        .setExpiration(expirationDate)
        .signWith(
            SignatureAlgorithm.HS256,
            key
        )
        .compact();
  }

  public static boolean isValid(String jwt) {
    SignatureAlgorithm sa = SignatureAlgorithm.HS256;
    SecretKeySpec secretKeySpec = new SecretKeySpec(key, sa.getJcaName());

    String[] jwtSplit = jwt.split("\\.");
    String tokenWithoutSignature = jwtSplit[0] + "." + jwtSplit[1];
    String signature = jwtSplit[2];

    DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, secretKeySpec);

    return validator.isValid(tokenWithoutSignature, signature);
  }

}
