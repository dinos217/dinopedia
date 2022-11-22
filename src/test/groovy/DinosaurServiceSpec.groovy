import com.project.dinopedia.dtos.DinosaurRequestDto
import com.project.dinopedia.entities.Dinosaur
import com.project.dinopedia.entities.Image
import com.project.dinopedia.repositories.DinosaurRepository
import com.project.dinopedia.repositories.ImageRepository
import com.project.dinopedia.services.DinosaurServiceImpl
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification

class DinosaurServiceSpec extends Specification {

    DinosaurRepository dinosaurRepository = Mock(DinosaurRepository)
    ImageRepository imageRepository = Mock(ImageRepository)

    DinosaurServiceImpl service = new DinosaurServiceImpl(dinosaurRepository, imageRepository)

    def "save a new Dinosaur without images"() {

        given: "a DinosaurRequestDto and an empty list of images"
        DinosaurRequestDto request = buildDinoRequestWithoutImages()

        Dinosaur dinosaur = buildDinosaur()

        when: "the service method is called"
        service.save(request, Collections.emptyList())

        then:
        1 * dinosaurRepository.existsByName(request.getName()) >> false
        1 * dinosaurRepository.save(_) >> dinosaur
    }

    def "delete a Dinosaur"() {

        given: "a Dinosaur id"
        Dinosaur dinosaur = buildDinosaur()

        when: "the service method is called"
        service.delete(1L)

        then:
        1 * dinosaurRepository.findById(1L) >> Optional.of(dinosaur)
        1 * dinosaurRepository.delete(dinosaur)

    }

    private static DinosaurRequestDto buildDinoRequestWithoutImages() {
        DinosaurRequestDto request = new DinosaurRequestDto()
        request.setName("Dino")
        request.setEatingClass("Carnivores")
        request.setPeriod("Jurassic")
        request.setSize("Big")
        request
    }

    private static Dinosaur buildDinosaur() {
        Dinosaur dinosaur = new Dinosaur()
        dinosaur.setName("Dino")
        dinosaur.setEatingClass("Carnivores")
        dinosaur.setPeriod("Jurassic")
        dinosaur.setSize("Big")
        dinosaur
    }
}
