package org.axrunner.resource;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.axrunner.api.entity.Event;
import org.axrunner.boundary.EventBoundary;
import org.axrunner.core.AxRunnerCoreService;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.fail;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 *
 */
public class EventResourceTest {

    private final EventBoundary eventBoundary = mock(EventBoundary.class);
    private final AxRunnerCoreService axRunnerCoreService = mock(AxRunnerCoreService.class);

    @Rule
    public final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new EventResource(eventBoundary, axRunnerCoreService))
            .build();


    @Before
    public void setup() {
        reset(eventBoundary, axRunnerCoreService);
    }

    @Test
    public void itShouldGetEvent() {
        String id = "test";
        String name = "test event";
        Date date = Date.from(Instant.now());

        org.axrunner.core.domain.Event domainEvent = new org.axrunner.core.domain.Event();
        domainEvent.setEventId(id);
        domainEvent.setName(name);
        domainEvent.setDate(date);

        Event apiEvent = new Event();
        apiEvent.setId(id);
        apiEvent.setName(name);
        apiEvent.setDate(date);

        when(axRunnerCoreService.getEvent(id)).thenReturn(domainEvent);
        when(eventBoundary.toApiEntity(domainEvent)).thenReturn(apiEvent);

        Event response = resources.client()
                .target("/events/" + id)
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(Event.class);

        assertThat(response).isEqualTo(apiEvent);
    }

    @Test
    public void itShouldRespondWithNotFoundErrorWhenEventIdInvalid() {

        String id = "test";
        when(axRunnerCoreService.getEvent(id)).thenReturn(null);

        try {
            Event response = resources.client()
                    .target("/events/" + id)
                    .request(MediaType.APPLICATION_JSON_TYPE)
                    .get(Event.class);

            fail("Expected ResourceException");
        } catch (NotFoundException nfe) {
            assertThat(nfe.getResponse().getStatusInfo().getStatusCode())
                    .isEqualTo(HttpStatus.NOT_FOUND_404);
        }
    }

}
