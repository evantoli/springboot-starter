package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Actor;
import au.com.softwarekitchen.persistence.ActorRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssemblerArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Locale;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
@EnableSpringDataWebSupport
public class ActorControllerTest {

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {

        final ActorRepository mockActorRepository = mock(ActorRepository.class);
        when(mockActorRepository.findOne(1L)).thenReturn(new Actor("Fred", "Black"));

        final ActorController actorController = new ActorController();
        Whitebox.setInternalState(actorController, "actorRepository", mockActorRepository);
        Whitebox.setInternalState(actorController, "actorResourceAssembler", new ActorResourceAssembler());

        mvc = MockMvcBuilders
                .standaloneSetup(
                        actorController)
                .setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver(),
                        new PagedResourcesAssemblerArgumentResolver(
                                new HateoasPageableHandlerMethodArgumentResolver(),
                                null))
                .setViewResolvers(new ViewResolver() {
                    @Override
                    public View resolveViewName(String viewName, Locale locale) throws Exception {
                        return new MappingJackson2JsonView();
                    }
                })
                .build();
    }

    @Test
    @Ignore
    public void getActors() throws Exception {
        final MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/actors/1").accept(MediaType.APPLICATION_JSON);

        mvc.perform(builder)
                .andExpect(status().isOk());
//                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }
}