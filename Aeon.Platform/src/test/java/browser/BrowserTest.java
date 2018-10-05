package browser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BrowserApp.class)
@AutoConfigureMockMvc
public class BrowserTest {

    @Autowired
    private MockMvc mvc;
    private String sessionId;

    private Properties settings;
    private String command;
    private List<Object> args;
    private ByWebArgs byWebArgs;

    private CreateSessionBody body;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        settings = null;
        command = "GoToUrlCommand";
        args = new ArrayList<>(Arrays.asList("https://google.com"));
        byWebArgs = new ByWebArgs("this", "css");

        body = new CreateSessionBody(settings, command, args, byWebArgs);
    }

    @Test
    public void launchBrowser() throws Exception {
        String json = mapper.writeValueAsString(body);

       // mvc.perform(get("/launch"))
        //        .andExpect(status().isOk());
        mvc.perform(post("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

//    @Test
//    public void navigateToUrl() {
//        mvc.perform(post())
//    }
//
//    @Test
//    public void quitBrowser() {
//        mvc.perform(post("/sessions/{sessionID}/execute").param())
//                .andExpect(status().isOk());
//    }
}
