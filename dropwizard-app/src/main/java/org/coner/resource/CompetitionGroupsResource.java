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

import org.coner.api.entity.CompetitionGroupApiEntity;
import org.coner.api.request.AddCompetitionGroupRequest;
import org.coner.api.response.ErrorsResponse;
import org.coner.api.response.GetCompetitionGroupsResponse;
import org.coner.boundary.CompetitionGroupApiAddPayloadBoundary;
import org.coner.boundary.CompetitionGroupApiDomainBoundary;
import org.coner.core.ConerCoreService;
import org.coner.core.domain.entity.CompetitionGroup;
import org.coner.core.domain.payload.CompetitionGroupAddPayload;
import org.eclipse.jetty.http.HttpStatus;

import io.dropwizard.hibernate.UnitOfWork;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/competitionGroups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(value = "Competition Groups")
public class CompetitionGroupsResource {

    private final ConerCoreService conerCoreService;
    private final CompetitionGroupApiDomainBoundary apiDomainBoundary;
    private final CompetitionGroupApiAddPayloadBoundary apiAddPayloadBoundary;

    @Inject
    public CompetitionGroupsResource(
            ConerCoreService conerCoreService,
            CompetitionGroupApiDomainBoundary competitionGroupApiDomainBoundary,
            CompetitionGroupApiAddPayloadBoundary apiAddPayloadBoundary
    ) {
        this.conerCoreService = conerCoreService;
        this.apiDomainBoundary = competitionGroupApiDomainBoundary;
        this.apiAddPayloadBoundary = apiAddPayloadBoundary;
    }

    @POST
    @UnitOfWork
    @ApiOperation(value = "Add a Competition Group")
    @ApiResponses({
            @ApiResponse(
                    code = HttpStatus.CREATED_201,
                    message = "Created at URI in Location header"
            ),
            @ApiResponse(
                    code = HttpStatus.UNPROCESSABLE_ENTITY_422,
                    message = "Failed validation",
                    response = ErrorsResponse.class
            )
    })
    public Response addCompetitionGroup(
            @Valid @ApiParam(value = "Competition Group") AddCompetitionGroupRequest request
    ) {
        CompetitionGroupAddPayload addPayload = apiAddPayloadBoundary.toRemoteEntity(request);
        CompetitionGroup domainEntity = conerCoreService.addCompetitionGroup(addPayload);
        CompetitionGroupApiEntity entity = apiDomainBoundary.toLocalEntity(domainEntity);
        return Response.created(UriBuilder.fromResource(CompetitionGroupResource.class)
                .build(entity.getId()))
                .build();
    }

    @GET
    @UnitOfWork
    @ApiOperation(value = "Get all Competition Groups", response = GetCompetitionGroupsResponse.class)
    public GetCompetitionGroupsResponse getCompetitionGroups() {
        List<CompetitionGroup> domainCompetitionGroups = conerCoreService.getCompetitionGroups();
        GetCompetitionGroupsResponse response = new GetCompetitionGroupsResponse();
        response.setCompetitionGroups(apiDomainBoundary.toLocalEntities(domainCompetitionGroups));
        return response;
    }
}
