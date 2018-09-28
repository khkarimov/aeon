package browser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BrowserApp.class)
@AutoConfigureMockMvc
public class BrowserTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void launchBrowser() throws Exception {
        mvc.perform(get("/launch"))
                .andExpect(status().isOk());
    }

    /*@Test
    public void closeBrowser() throws Exception {
        mvc.perform(get("/close"))
                .andExpect(status().isOk());
    }*/
}
