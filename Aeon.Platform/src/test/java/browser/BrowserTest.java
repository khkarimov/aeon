package browser;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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

    @Test
    public void launchBrowserTest() throws Exception {
        body = new CreateSessionBody(settings, command, args, byWebArgs);

        String json = mapper.writeValueAsString(body);

        MvcResult result = mvc.perform(post("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        sessionId = result.getResponse().getContentAsString();
    }

    @Test
    public void launchWithSettingsTest() throws Exception {
        settings = new Properties();
        settings.setProperty("aeon.browser", "Firefox");
        body = new CreateSessionBody(settings, command, args, byWebArgs);

        String json = mapper.writeValueAsString(body);

        System.out.println("\njson body: " + json.toString() + "\n\n");

        MvcResult result = mvc.perform(post("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        sessionId = result.getResponse().getContentAsString();
    }

    @Test
    public void launchAndCloseTest() throws Exception {
        command = "QuitCommand";
        body = new CreateSessionBody(settings, command, args, byWebArgs);

        String json = mapper.writeValueAsString(body);

        MvcResult result = mvc.perform(post("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        sessionId = result.getResponse().getContentAsString();

        mvc.perform(post("/sessions/{sessionID}/execute", sessionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void goToUrlTest() throws Exception {
        command = "GoToUrlCommand";
        args = new ArrayList<>(Arrays.asList("https://google.com"));
        body = new CreateSessionBody(settings, command, args, byWebArgs);

        String json = mapper.writeValueAsString(body);

        MvcResult result = mvc.perform(post("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        sessionId = result.getResponse().getContentAsString();

        mvc.perform(post("/sessions/{sessionID}/execute", sessionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
