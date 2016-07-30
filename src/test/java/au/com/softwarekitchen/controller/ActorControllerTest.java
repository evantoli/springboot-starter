package au.com.softwarekitchen.controller;

import au.com.softwarekitchen.model.Actor;
import au.com.softwarekitchen.persistence.ActorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssemblerArgumentResolver;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.core.AnnotationRelProvider;
import org.springframework.hateoas.core.DelegatingRelProvider;
import org.springframework.hateoas.core.EvoInflectorRelProvider;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.ControllerLinkBuilderFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockServletContext;
import org.springframework.plugin.core.OrderAwarePluginRegistry;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;
import java.util.Locale;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class ActorControllerTest {

    private MockMvc mvc;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private ActorResourceAssembler actorResourceAssembler;

    @InjectMocks
    private ActorController actorController;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        final Actor fred = new Actor("Fred", "Black");
        fred.setId(1L);

        final Actor mary = new Actor("Mary", "White");
        mary.setId(2L);

        final Page<Actor> actors = new PageImpl<Actor>(Arrays.asList(new Actor[]{fred, mary}));

        when(actorRepository.findOne(fred.getId())).thenReturn(fred);
        when(actorRepository.findOne(mary.getId())).thenReturn(mary);
        when(actorRepository.findAll(any(Pageable.class))).thenReturn(actors);

        when(actorResourceAssembler.toResource(any(Actor.class))).thenCallRealMethod();
        when(actorResourceAssembler.toResources(any(Iterable.class))).thenCallRealMethod();

        // Create an object mapper to ensure we get HAL+JSON responses from our
        // Mock controllers.
        final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                .modules(new Jackson2HalModule())
                .handlerInstantiator(new Jackson2HalModule.HalHandlerInstantiator(
                        new DelegatingRelProvider(
                                OrderAwarePluginRegistry.create(Arrays.asList(
                                        new EvoInflectorRelProvider(),
                                        new AnnotationRelProvider()))),
                        null, null))
                .build();

        mvc = MockMvcBuilders
                .standaloneSetup(actorController)
                .setCustomArgumentResolvers(
                        new PageableHandlerMethodArgumentResolver(),
                        new PagedResourcesAssemblerArgumentResolver(
                                new HateoasPageableHandlerMethodArgumentResolver(),
                                new ControllerLinkBuilderFactory()))
                .setMessageConverters(
                        new MappingJackson2HttpMessageConverter(objectMapper))
                .setViewResolvers((String e, Locale l) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    public void getActorByIdTest() throws Exception {
        final MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/actors/1")
                        .contentType(MediaTypes.HAL_JSON)
                        .accept(MediaTypes.HAL_JSON);

        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Fred")))
                .andExpect(jsonPath("$.lastName", is("Black")));
    }

    @Test
    public void getActors() throws Exception {
        final MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.get("/actors")
                        .contentType(MediaTypes.HAL_JSON)
                        .accept(MediaTypes.HAL_JSON);

        mvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(jsonPath("$._links.self.href", endsWith("actors")))
                .andExpect(jsonPath("$._embedded.actors.[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.actors.[0].firstName", is("Fred")))
                .andExpect(jsonPath("$._embedded.actors.[0].lastName", is("Black")))
                .andExpect(jsonPath("$._embedded.actors.[1].id", is(2)))
                .andExpect(jsonPath("$._embedded.actors.[1].firstName", is("Mary")))
                .andExpect(jsonPath("$._embedded.actors.[1].lastName", is("White")))
                .andExpect(jsonPath("$.page.size", is(0)))
                .andExpect(jsonPath("$.page.totalElements", is(2)))
                .andExpect(jsonPath("$.page.totalPages", is(1)))
                .andExpect(jsonPath("$.page.number", is(0)));
    }
}