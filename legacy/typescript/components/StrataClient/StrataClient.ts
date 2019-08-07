// ##########################################################################
// # File Name:	StrataClient.ts
// #
// # Copyright:	2016, Sapientia Systems, LLC. All Rights Reserved.
// #
// # License:	This file is part of the StrataClient Framework.
// #
// #   			The StrataClient Framework is free software: you 
// #			can redistribute it and/or modify it under the terms of 
// #			the GNU Lesser General Public License as published by
// #    		the Free Software Foundation, either version 3 of the 
// #			License, or (at your option) any later version.
// #
// #    		The StrataClient Framework is distributed in the 
// #			hope that it will be useful, but WITHOUT ANY WARRANTY; 
// #			without even the implied warranty of MERCHANTABILITY or 
// #			FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser 
// #			General Public License for more details.
// #
// #    		You should have received a copy of the GNU Lesser 
// #			General Public License along with the StrataClient
// #			Framework. If not, see http://www.gnu.org/licenses/.
// ##########################################################################

/////////////////////////////////////////////////////////////////////////////

module Strata1.Client.Command
{
    export
    interface ICommand
    {
        execute(): void;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Client.Command
{
    export
    interface INullProvider { }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Client.Event
{
    export
    interface IHandler<E>
    {
        handle(event: E): void;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Client.Event
{
    export
    interface IChangeEvent
    {
        getSender<T>(): T;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Client.Event
{
    export abstract
    class AbstractChangeEvent
        implements IChangeEvent
    {
        private sender: any;

        constructor(sender: any)
        {
            this.sender = sender;
        }

        getSender<T>(): T
        {
            return <T>this.sender;
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Client.Event
{
    export
    interface IChangeEventProcessor
    {
        processChange(change: IChangeEvent): void;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Client.View
{
    export
    interface IView<P>
    {
        setProvider(provider: P): void;

        getName(): string;
        getProvider(): P;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Client.View
{
    import IChangeEvent = Strata1.Client.Event.IChangeEvent;
    import IChangeEventProcessor = Strata1.Client.Event.IChangeEventProcessor;
    import IHandler = Strata1.Client.Event.IHandler;

    export abstract
    class AbstractView<P>
        implements IView<P>,IChangeEventProcessor
    {
        private name: string;
        private provider: P;
        private handlers: { [key: string]: (e:IChangeEvent)=>void };

        constructor(name: string)
        {
            this.name = name;
            this.provider = null;
            this.handlers = {};
        }

        processChange(change: IChangeEvent): void
        {
            this.handlers[typeof change](change);
        }

        setProvider(provider: P): void
        {
            this.provider = provider;
        }

        getName(): string
        {
            return this.name;
        }

        getProvider(): P
        {
            return this.provider;
        }

        protected
        setHandler(key: string,handler: (e:IChangeEvent)=>void): void
        {
            this.handlers[key] = handler;
        }

    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Client.Model
{
    import IChangeEventProcessor = Strata1.Client.Event.IChangeEventProcessor;
    import IChangeEvent = Strata1.Client.Event.IChangeEvent;

    export
    interface IModel
    {
        setProcessor(processor: IChangeEventProcessor): void;

        getProcessor(): IChangeEventProcessor;

        hasProcessor(): boolean;

        notifyChange(change: IChangeEvent): void;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Client.Model
{
    import IChangeEventProcessor = Strata1.Client.Event.IChangeEventProcessor;
    import IChangeEvent = Strata1.Client.Event.IChangeEvent;

    export abstract
    class AbstractModel
        implements IModel
    {
        private processor: IChangeEventProcessor;

        constructor()
        {
            this.processor = null;
        }

        setProcessor(processor: IChangeEventProcessor): void
        {
            this.processor = processor;
        }

        getProcessor(): IChangeEventProcessor
        {
            return this.processor;
        }

        hasProcessor(): boolean
        {
            return this.processor != null;
        }

        notifyChange(change: IChangeEvent): void
        {
            if(this.hasProcessor())
                this.processor.processChange(change);
        }
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Client.Controller
{
    import IView = Strata1.Client.View.IView;
    import IModel = Strata1.Client.Model.IModel;
    import IChangeEventProcessor = Strata1.Client.Event.IChangeEventProcessor;
    import IChangeEvent = Strata1.Client.Event.IChangeEvent;

    export
    interface IController<P,V extends IView<any>,M extends IModel>
        extends IChangeEventProcessor
    {
        getView(): V;

        getModel(): M;

        start(): void;

        stop(): void;
    }
}

/////////////////////////////////////////////////////////////////////////////

module Strata1.Client.Controller
{
    import IView = Strata1.Client.View.IView;
    import IModel = Strata1.Client.Model.IModel;
    import IChangeEventProcessor = Strata1.Client.Event.IChangeEventProcessor;
    import IChangeEvent = Strata1.Client.Event.IChangeEvent;
    import IHandler = Strata1.Client.Event.IHandler;

    export abstract
    class AbstractController<P,V extends IView<any>,M extends IModel>
        implements IController<P,V,M>
    {
        private handlers: { [key: string]: (e: IChangeEvent) => void };
        private view: V;
        private model: M;

        constructor()
        {
            this.handlers = {};
            this.view = null;
            this.model = null;
        }

        processChange(change: IChangeEvent): void
        {
            this.handlers[typeof change](change);
        }

        getView(): V
        {
            return this.view;
        }

        getModel(): M
        {
            return this.model;
        }

        abstract
        start(): void;

        abstract
        stop(): void;

        protected
        setHandler(key: string,handler: (e:IChangeEvent)=>void): void
        {
            this.handlers[key] = handler;
        }

        protected
        setView(view: V,downcastedThis: P): void
        {
            this.view = view;
            this.view.setProvider( this );
        }

        protected
        setModel(model: M): void
        {
            this.model = model;
            this.model.setProcessor(this);
        }
    }
}

// ##########################################################################
