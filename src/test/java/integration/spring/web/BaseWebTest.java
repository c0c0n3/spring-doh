package integration.spring.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static util.Arrayz.isNullOrZeroLength;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import util.servlet.http.CharEncodingFilter;
import app.config.Profiles;
import app.config.WebWiring;
import app.config.Wiring;

@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration(classes = Wiring.class),
        @ContextConfiguration(classes = WebWiring.class)
})
@ActiveProfiles({Profiles.HardCodedConfig, Profiles.WebApp})
public class BaseWebTest {
    
    @Autowired
    protected WebApplicationContext wac;
    
    protected MockMvc mockMvc;
    
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                 .webAppContextSetup(wac)
                 .addFilters(
                         CharEncodingFilter.Utf8Request(), 
                         CharEncodingFilter.Utf8Response())
                 .build();
        
        additionalSetup();
    }
    
    protected void additionalSetup() { }
    
    protected ResultActions doGet(String url, MediaType...ts) throws Exception {
        MockHttpServletRequestBuilder request = get(url);
        if (!isNullOrZeroLength(ts)) {
            request.accept(ts);
        }
        return mockMvc
              .perform(request)
              .andDo(print());  // comment this in/out to see/hide Spring dump
    }
    
    protected String doGetAndReturnResponseBody(String url) throws Exception {
        return doGet(url).andReturn().getResponse().getContentAsString();
    }
    
    protected String tellId(String relPath) throws Exception {
        return doGetAndReturnResponseBody("/" + relPath);
    }
    
    protected ResultActions expectTextUtf8ContentType(ResultActions x) 
            throws Exception {
        return x
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
        .andExpect(content().encoding("UTF-8"));
    }
    
}
