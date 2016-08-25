package au.com.softwarekitchen.hateoas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by evantoliopoulos on 12/08/2016.
 */
public abstract class HALResource<T> extends Resource<T> {

    private final Map<String, ResourceSupport> embedded = new HashMap<String, ResourceSupport>();

    public HALResource(T content, Link... links) {
        super(content, links);
    }

    public HALResource(T content, Iterable<Link> links) {
        super(content, links);
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("_embedded")
    public Map<String, ResourceSupport> getEmbeddedResources() {
        return embedded;
    }

    public void embedResource(String relationship, ResourceSupport resource) {
        embedded.put(relationship, resource);
    }
}