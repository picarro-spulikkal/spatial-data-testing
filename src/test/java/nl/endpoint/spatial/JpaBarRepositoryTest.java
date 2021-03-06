package nl.endpoint.spatial;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.vividsolutions.jts.geom.Coordinate;
import nl.endpoint.spatial.spring.SpatialAwareFlatXmlDataSetLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.extractProperty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection", dataSetLoader = SpatialAwareFlatXmlDataSetLoader.class)
public class JpaBarRepositoryTest {

    @Autowired
    private BarRepository barRepository;

    @Test
    @DatabaseSetup("knownBars-flatXml.xml")
    public void findByLocationShouldReturnAllBarsWithinRange() throws Exception {
        List<Bar> bars = this.barRepository.findByLocation(new Coordinate(100, 100), 50);
        assertThat(extractProperty("name").from(bars))
                .containsOnly("Cafe 't Haantje", "Cafe The Hide Away");
    }

}

