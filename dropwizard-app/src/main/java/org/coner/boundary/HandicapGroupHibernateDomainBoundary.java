package org.coner.boundary;

import javax.inject.Inject;

import org.coner.core.domain.entity.HandicapGroup;
import org.coner.hibernate.entity.HandicapGroupHibernateEntity;
import org.coner.util.merger.ObjectMerger;
import org.coner.util.merger.ReflectionJavaBeanMerger;

public class HandicapGroupHibernateDomainBoundary extends AbstractBoundary<
        HandicapGroupHibernateEntity,
        HandicapGroup> {

    @Inject
    public HandicapGroupHibernateDomainBoundary() {
    }

    @Override
    protected ObjectMerger<HandicapGroupHibernateEntity, HandicapGroup> buildLocalToRemoteMerger() {
        return new ReflectionJavaBeanMerger<>();
    }

    @Override
    protected ObjectMerger<HandicapGroup, HandicapGroupHibernateEntity> buildRemoteToLocalMerger() {
        return new ReflectionJavaBeanMerger<>();
    }
}
