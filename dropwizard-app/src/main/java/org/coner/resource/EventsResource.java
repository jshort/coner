package org.coner.resource;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.coner.api.entity.EventApiEntity;
import org.coner.api.request.AddEventRequest;
import org.coner.api.response.ErrorsResponse;
import org.coner.api.response.GetEventsResponse;
import org.coner.boundary.EventApiAddPayloadBoundary;
import org.coner.boundary.EventApiDomainBoundary;
import org.coner.core.ConerCoreService;
import org.coner.core.domain.entity.Event;
import org.coner.core.domain.payload.EventAddPayload;
import org.eclipse.jetty.http.HttpStatus;

import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "Events")
public class EventsResource {

    private final ConerCoreService conerCoreService;
    private final EventApiDomainBoundary apiDomainEntityBoundary;
    private final EventApiAddPayloadBoundary apiAddPayloadBoundary;

    @Inject
    public EventsResource(
            ConerCoreService conerCoreService,
            EventApiDomainBoundary eventApiDomainBoundary,
            EventApiAddPayloadBoundary eventApiAddPayloadBoundary
    ) {
        this.conerCoreService = conerCoreService;
        this.apiDomainEntityBoundary = eventApiDomainBoundary;
        this.apiAddPayloadBoundary = eventApiAddPayloadBoundary;
    }

    @GET
    @UnitOfWork
    @ApiOperation(value = "Get a list of all events", response = GetEventsResponse.class)
    public GetEventsResponse getEvents() {
        List<Event> domainEvents = conerCoreService.getEvents();
        GetEventsResponse response = new GetEventsResponse();
        response.setEvents(apiDomainEntityBoundary.toLocalEntities(domainEvents));
        return response;
    }

    @POST
    @UnitOfWork
    @ApiOperation(value = "Add an Event", response = Response.class)
    @ApiResponses({
            @ApiResponse(
                    code = HttpStatus.CREATED_201,
                    response = Void.class,
                    message = "Created at URI in Location header"
            ),
            @ApiResponse(
                    code = HttpStatus.UNPROCESSABLE_ENTITY_422,
                    response = ErrorsResponse.class,
                    message = "Failed validation"
            )
    })
    public Response addEvent(
            @Valid @ApiParam(value = "Event", required = true) AddEventRequest request
    ) {
        EventAddPayload addPayload = apiAddPayloadBoundary.toRemoteEntity(request);
        Event domainEntity = conerCoreService.addEvent(addPayload);
        EventApiEntity eventApiEntity = apiDomainEntityBoundary.toLocalEntity(domainEntity);
        return Response.created(UriBuilder.fromResource(EventResource.class)
                .build(eventApiEntity.getId()))
                .build();
    }
}
