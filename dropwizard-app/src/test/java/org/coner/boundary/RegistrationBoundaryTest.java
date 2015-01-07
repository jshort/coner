package org.coner.boundary;

import org.coner.core.domain.Registration;
import org.coner.util.ApiEntityUtils;
import org.coner.util.DomainUtils;
import org.coner.util.TestConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 *
 */
public class RegistrationBoundaryTest {

    private final EventBoundary eventBoundary = mock(EventBoundary.class);

    private final String registrationID = TestConstants.REGISTRATION_ID;
    private final String registrationFirstName = TestConstants.REGISTRATION_FIRSTNAME;
    private final String registrationLastName = TestConstants.REGISTRATION_LASTNAME;

    private RegistrationBoundary registrationBoundary;

    @Before
    public void setup() {
        RegistrationBoundary.setInstance(null);
        registrationBoundary = new RegistrationBoundary(eventBoundary);
    }

    @After
    public void tearDown() {
        EventBoundary.setInstance(null);
    }

    @Test
    public void whenGetInstanceItShouldInstantiateAndReturnSingleton() {
        RegistrationBoundary one = RegistrationBoundary.getInstance();
        RegistrationBoundary two = RegistrationBoundary.getInstance();

        assertThat(one)
                .isNotNull()
                .isSameAs(two);
    }

    @Test
    public void whenMergeApiIntoDomainItShouldMerge() {
        org.coner.api.entity.Registration apiRegistration = ApiEntityUtils.fullApiRegistration();
        Registration domainRegistration = new Registration();

        registrationBoundary.merge(apiRegistration, domainRegistration);

        assertThat(domainRegistration.getId()).isEqualTo(registrationID);
        assertThat(domainRegistration.getFirstName()).isEqualTo(registrationFirstName);
        assertThat(domainRegistration.getLastName()).isEqualTo(registrationLastName);
        assertThat(domainRegistration.getEvent().getId())
                .isEqualTo(DomainUtils.fullDomainEvent().getId());
    }

    @Test
    public void whenMergeApiWithoutEventIntoDomainItShouldNpe() {
        org.coner.api.entity.Registration apiRegistration = ApiEntityUtils.fullApiRegistration();
        apiRegistration.setEvent(null);
        Registration domainRegistration = new Registration();

        try {
            registrationBoundary.merge(apiRegistration, domainRegistration);
            failBecauseExceptionWasNotThrown(NullPointerException.class);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(NullPointerException.class);
        }
    }

    @Test
    public void whenMergeDomainIntoApiItShouldMerge() {
        Registration domainRegistration = DomainUtils.fullDomainRegistration();
        org.coner.api.entity.Registration apiRegistration = new org.coner.api.entity.Registration();
        org.coner.api.entity.Registration.Event apiEvent = ApiEntityUtils.partialApiEvent();

        registrationBoundary.merge(domainRegistration, apiRegistration);

        assertThat(apiRegistration.getId()).isEqualTo(registrationID);
        assertThat(apiRegistration.getFirstName()).isEqualTo(registrationFirstName);
        assertThat(apiRegistration.getLastName()).isEqualTo(registrationLastName);
        assertThat(apiRegistration.getEvent())
                .isEqualTo(apiEvent)
                .isNotSameAs(apiEvent);
    }
}