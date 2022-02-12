package UniCinemaTest.ShowManager.service;

import ShowManager.service.ShowServiceMethods;
import model.bean.Show;
import model.dao.RoomDAO;
import model.dao.ShowDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

public class ShowServiceMethodsTestVintage {


    @Mock
    private ShowDAO showDAO;


    private ShowServiceMethods showServiceMethods;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        showServiceMethods = new ShowServiceMethods();
        showServiceMethods.setShowDAO(showDAO);
    }


    @Test
    public void fetchAll() throws SQLException {
        ArrayList<Show> showList = new ArrayList<>();
        when(showDAO.fetchAll()).thenReturn(showList);
        assertEquals(showServiceMethods.fetchAll(), showList);
    }

}
