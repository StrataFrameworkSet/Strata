//////////////////////////////////////////////////////////////////////////////
// HelloWorldPresenter.java
//////////////////////////////////////////////////////////////////////////////

package strata.client.core.presentation;

import strata.client.core.presenter.AbstractPresenter;
import strata.client.core.presenter.IModelStore;
import strata.client.core.presenter.SimpleAction;

public
class HelloWorldPresenter
    extends AbstractPresenter<IHelloWorldModel,IHelloWorldView>
    implements IHelloWorldPresenter
{
    public
    HelloWorldPresenter(IModelStore modelStore,IHelloWorldViewCreator creator)
    {
        super(modelStore,IHelloWorldModel.class);
        setView(creator.create(this));
    }

    @Override
    public void
    submit()
    {
        dispatch(
            new SimpleAction<>(
                getKey(),
                model ->
                    new HelloWorldModel(
                        getView().getName(),
                        getView().getGreeting())));
    }

    @Override
    public void
    exit()
    {
        dispatch(
            new SimpleAction<>(
                getKey(),
                model ->
                {
                    System.exit(1);
                    return model;
                })
        );
    }

    @Override
    public void
    start()
    {

    }

    @Override
    public void
    stop()
    {

    }

    @Override
    protected void
    doUpdate(IHelloWorldView view,IHelloWorldModel model)
    {
        view.setPersonalizedGreeting(model.getPersonalizedGreeting());

    }

}

//////////////////////////////////////////////////////////////////////////////
