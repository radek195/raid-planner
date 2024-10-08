import com.example.raid_planner.RaidPlannerApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(classes = RaidPlannerApplication)
@ActiveProfiles("test")
class RaidPlannerApplicationTest extends Specification {

    def "Should start"() {
        expect:
            true
    }
}
