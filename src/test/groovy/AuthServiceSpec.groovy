import com.project.dinopedia.dtos.LoginRequestDto
import com.project.dinopedia.services.AuthService
import com.project.dinopedia.services.AuthServiceImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import spock.lang.Specification

class AuthServiceSpec extends Specification {

    AuthenticationManager authenticationManager = Mock(AuthenticationManager)
    AuthService service = new AuthServiceImpl(authenticationManager)

    def "login as an existing user"() {

        given: "a login request from the user"
        LoginRequestDto loginRequestDto = new LoginRequestDto()
        loginRequestDto.setUsername("tommy")
        loginRequestDto.setPassword("123")

        when: "the login service method is called"
        service.authenticate(loginRequestDto)

        then:
        1 * authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDto.getUsername(), loginRequestDto.getPassword()))
    }

}
