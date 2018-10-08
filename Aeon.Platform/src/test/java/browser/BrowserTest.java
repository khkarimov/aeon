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

import java.lang.reflect.Array;
import java.util.*;

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
    private List<String> byWebArgs;
    private CreateSessionBody body;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void launchBrowserTest() throws Exception {
        body = new CreateSessionBody(settings, command, args, byWebArgs);

        String json = mapper.writeValueAsString(body);

        mvc.perform(post("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void launchWithSettingsTest() throws Exception {
        settings = new Properties();
        settings.setProperty("aeon.browser", "Firefox");
        body = new CreateSessionBody(settings, command, args, byWebArgs);

        String json = mapper.writeValueAsString(body);

        mvc.perform(post("/sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
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
        args = new ArrayList<>(Collections.singletonList("https://google.com"));
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
    public void elementExistsTest() throws Exception {
        command = "ExistsCommand";
        args = new ArrayList<>(Arrays.asList("selector", "initializer"));
        byWebArgs = new ArrayList<>(Arrays.asList("*", "css"));
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
    public void clickElementTest() throws Exception {
        command = "GoToUrlCommand";
        args = new ArrayList<>(Collections.singletonList("https://google.com"));
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

        command = "ClickCommand";
        args = new ArrayList<>(Arrays.asList("selector", "initializer"));
        byWebArgs = new ArrayList<>(Arrays.asList("a", "css"));
        body = new CreateSessionBody(settings, command, args, byWebArgs);

        json = mapper.writeValueAsString(body);

        mvc.perform(post("/sessions/{sessionID}/execute", sessionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
