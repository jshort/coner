package org.coner.boundary;

import org.coner.core.domain.CompetitionGroupSet;
import org.coner.hibernate.entity.CompetitionGroupSetHibernateEntity;
import org.coner.util.merger.*;

public class CompetitionGroupSetHibernateDomainBoundary extends AbstractBoundary<
        CompetitionGroupSetHibernateEntity,
        CompetitionGroupSet> {

    @Override
    protected ObjectMerger<CompetitionGroupSetHibernateEntity, CompetitionGroupSet> buildLocalToRemoteMerger() {
        return new ReflectionJavaBeanMerger<>((source, destination) -> {
            destination.setId(source.getCompetitionGroupSetId());
        });
    }

    @Override
    protected ObjectMerger<CompetitionGroupSet, CompetitionGroupSetHibernateEntity> buildRemoteToLocalMerger() {
        return new ReflectionJavaBeanMerger<>((source, destination) -> {
            destination.setCompetitionGroupSetId(source.getId());
        });
    }
}
