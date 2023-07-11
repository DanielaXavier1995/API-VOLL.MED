package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("CHAMANDO FILTER");

        var tokenJWT = recuperarToken(request); //Recuperando o token do Header

        if(tokenJWT != null) { //Se o token estiver vindo valide
            var subject = tokenService.getSubject(tokenJWT); //Valida o token e recupera o subject

            //Recuperando e autenticando o usuário na requisição (Autenticação forçada)
            var usuario = usuarioRepository.findByLogin(subject);
            var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); //Classe do Spring que representa um usuário logado no sistema
            SecurityContextHolder.getContext().setAuthentication(authentication); //Força a autenticação
            System.out.println("LOGADO NA REQUISIÇÃO");
        }

        filterChain.doFilter(request,response); //Segue o fluxo da requisição
    }

    private String recuperarToken(HttpServletRequest request) {

        var authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", ""); //Se estiver vindo o Header
        }
        return null; //Se não estiver vindo o Header(Ex: login) retorne nulo
    }
}
