//////////////////////////////////////////////////////////////////////////////
// TestModule.java
//////////////////////////////////////////////////////////////////////////////

package strata.application.core.interception;

import com.google.inject.matcher.Matchers;
import org.hibernate.cfg.Configuration;
import strata.application.core.action.IActionQueue;
import strata.application.core.action.StandardActionQueue;
import strata.domain.core.testdomain.IPersonRepository;
import strata.domain.core.testdomain.PersonRepository;
import strata.domain.core.unitofwork.IUnitOfWorkProvider;
import strata.domain.hibernate.unitofwork.HibernateUnitOfWorkProvider;
import strata.foundation.core.inject.AbstractModule;
import strata.foundation.core.inject.GuiceThreadScope;

public
class TestModule
    extends AbstractModule
{
    public
    TestModule()
    {
        setDefaultScope(new GuiceThreadScope());
    }

    @Override
    protected void
    configure()
    {
        bind(IFoo.class)
            .to(Foo.class)
            .in(getDefaultScope());

        bind(IPersonService.class)
            .to(PersonService.class)
            .in(getDefaultScope());

        bind(IPersonRepository.class)
            .to(PersonRepository.class)
            .in(getDefaultScope());

        bind(IActionQueue.class)
            .to(StandardActionQueue.class)
            .in(getDefaultScope());

        bind(IUnitOfWorkProvider.class)
            .toProvider(
                ()->
                {
                    Configuration configuration = new Configuration();

                    configuration.configure();

                    return
                        new HibernateUnitOfWorkProvider(
                            configuration.buildSessionFactory());
                })
            .in(getDefaultScope());

        bindInterceptor(
            Matchers.any(),
            Matchers.annotatedWith(UnitOfWork.class),
            new UnitOfWorkInterceptor(3));

        bindInterceptor(
            Matchers.any(),
            Matchers.annotatedWith(Timed.class),
            new TimingMetricsInterceptor());


    }
}

//////////////////////////////////////////////////////////////////////////////
