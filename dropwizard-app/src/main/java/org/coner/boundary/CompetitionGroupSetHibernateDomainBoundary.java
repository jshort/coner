package org.coner.boundary;

import javax.inject.Inject;

import org.coner.core.domain.entity.CompetitionGroupSet;
import org.coner.hibernate.entity.CompetitionGroupSetHibernateEntity;
import org.coner.util.merger.CompositeMerger;
import org.coner.util.merger.ObjectMerger;
import org.coner.util.merger.ReflectionJavaBeanMerger;

public class CompetitionGroupSetHibernateDomainBoundary extends AbstractBoundary<
        CompetitionGroupSetHibernateEntity,
        CompetitionGroupSet> {

    @Inject
    public CompetitionGroupSetHibernateDomainBoundary() {
    }

    @Override
    protected ObjectMerger<CompetitionGroupSetHibernateEntity, CompetitionGroupSet> buildLocalToRemoteMerger() {
        return new CompositeMerger<>(
                new ReflectionJavaBeanMerger<>(),
                (source, destination) -> {
                    destination.setId(source.getCompetitionGroupSetId());
                }
        );
    }

    @Override
    protected ObjectMerger<CompetitionGroupSet, CompetitionGroupSetHibernateEntity> buildRemoteToLocalMerger() {
        return new CompositeMerger<>(
                new ReflectionJavaBeanMerger<>(),
                (source, destination) -> {
                    destination.setCompetitionGroupSetId(source.getId());
                }
        );
    }
}
