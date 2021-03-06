package org.coner.core.domain.service;

import javax.inject.Inject;

import org.coner.core.domain.entity.CompetitionGroupSet;
import org.coner.core.domain.payload.CompetitionGroupSetAddPayload;
import org.coner.core.gateway.CompetitionGroupSetGateway;

public class CompetitionGroupSetService extends AbstractDomainService<
        CompetitionGroupSet,
        CompetitionGroupSetAddPayload,
        CompetitionGroupSetGateway> {

    @Inject
    public CompetitionGroupSetService(CompetitionGroupSetGateway gateway) {
        super(CompetitionGroupSet.class, gateway);
    }
}
