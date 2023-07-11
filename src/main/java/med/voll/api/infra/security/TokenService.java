package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import med.voll.api.domain.usuario.UsuarioEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
@Service
public class TokenService {
    @Value("&{api.security.token.secret}")
    private String secret;
    public String gerarToken(UsuarioEntity usuario) {

        try {
           var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Voll.med") //Quem gera o token
                    .withSubject(usuario.getLogin()) //Quem é o usuario
                    .withExpiresAt(dataExpiracao()) //Quando vai expirar
                    .sign(algoritmo); //Assinatura
        } catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar token JWT", exception);
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.require(algoritmo)
                    .withIssuer("API Voll.med")
                    .build()
                    .verify((tokenJWT)) //Verificar se o token está válido de acordo com o algorítmo e o Issuer
                    //Se o bloco acima estiver Ok continua a execução(linha 39), caso contrário lança uma exception (linha 42);
                    .getSubject(); //Recupera o login
        } catch (JWTVerificationException exception){
            throw new RuntimeException("Token JWT inválido ou expirado!");
        }
    }
    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
