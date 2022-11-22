import com.project.dinopedia.dtos.VoteRequestDto
import com.project.dinopedia.entities.Dinosaur
import com.project.dinopedia.entities.User
import com.project.dinopedia.entities.Vote
import com.project.dinopedia.repositories.DinosaurRepository
import com.project.dinopedia.repositories.UserRepository
import com.project.dinopedia.repositories.VoteRepository
import com.project.dinopedia.services.VoteServiceImpl
import spock.lang.Specification

class VoteServiceSpec extends Specification {

    UserRepository userRepository = Mock(UserRepository)
    DinosaurRepository dinosaurRepository = Mock(DinosaurRepository)
    VoteRepository voteRepository = Mock(VoteRepository)

    VoteServiceImpl service = new VoteServiceImpl(userRepository, dinosaurRepository, voteRepository)

    def "save a new vote"() {

        given: "a VoteRequestDto"
        VoteRequestDto requestDto = new VoteRequestDto()
        requestDto.setUserId(1L)
        requestDto.setDinosaurId(1L)
        requestDto.setLike(true)

        User user = new User()
        user.setId(1L)

        Dinosaur dinosaur = new Dinosaur()
        dinosaur.setId(1L)
        dinosaur.setName("Dino")

        Vote vote = new Vote()
        vote.setId(1L)
        vote.setUser(user)
        vote.setDinosaur(dinosaur)

        when: "the service save method is called"
        service.save(requestDto)

        then:
        1 * userRepository.findById(1L) >> Optional.of(user)
        1 * dinosaurRepository.findById(1L) >> Optional.of(dinosaur)
        1 * voteRepository.existsByUserAndDinosaur(user, dinosaur) >> false
        1 * voteRepository.save(_) >> vote
    }
}
