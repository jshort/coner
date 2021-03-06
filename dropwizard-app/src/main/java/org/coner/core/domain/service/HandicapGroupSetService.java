package org.coner.core.domain.service;

import javax.inject.Inject;

import org.coner.core.domain.entity.HandicapGroupSet;
import org.coner.core.domain.payload.HandicapGroupSetAddPayload;
import org.coner.core.gateway.HandicapGroupSetGateway;

public class HandicapGroupSetService extends AbstractDomainService<
        HandicapGroupSet,
        HandicapGroupSetAddPayload,
        HandicapGroupSetGateway> {

    @Inject
    public HandicapGroupSetService(HandicapGroupSetGateway gateway) {
        super(HandicapGroupSet.class, gateway);
    }
}
