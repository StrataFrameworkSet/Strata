using System;
using System.ServiceModel.Channels;
using System.ServiceModel.Description;
using System.ServiceModel.Dispatcher;

namespace Capgroup.Xwing.WcfIntegration.Unity
{
    ///<summary>
    /// A policy injection behavior <see cref="Attribute"/> which replaces the default <see cref="DispatchRuntime.InstanceProvider"/> with 
    /// a <see cref="UnityInstanceProvider"/>.  This attribute enables the class for Unity Policy Injection.
    ///</summary>
    public class UnityContractBehaviorAttribute : Attribute, IContractBehavior, IContractBehaviorAttribute
    {

        #region Implementation of IContractBehavior

        /// <summary>
        /// Implement to confirm that the contract and endpoint can support the contract behavior.
        /// </summary>
        /// <param name="contractDescription">The contract to validate.</param>
        /// <param name="endpoint">The endpoint to validate.</param>
        public void Validate(ContractDescription contractDescription, ServiceEndpoint endpoint)
        {
            // No validation routines yet.
        }

        /// <summary>
        /// Implements a modification or extension of the client across a contract.  This implementation replaces the default InstanceProvider
        /// with the <see cref="UnityInstanceProvider"/>.
        /// </summary>
        /// <param name="contractDescription">The contract description to be modified.</param>
        /// <param name="endpoint">The endpoint that exposes the contract.</param>
        /// <param name="dispatchRuntime">The dispatch runtime that controls service execution.</param>
        public void ApplyDispatchBehavior(ContractDescription contractDescription, ServiceEndpoint endpoint, DispatchRuntime dispatchRuntime)
        {
            Type contractType = contractDescription.ContractType;
            dispatchRuntime.InstanceProvider = new UnityInstanceProvider(contractType);
        }

        /// <summary>
        /// Implements a modification or extension of the client across a contract.
        /// </summary>
        /// <param name="contractDescription">The contract description for which the extension is intended.</param>
        /// <param name="endpoint">The endpoint.</param>
        /// <param name="clientRuntime">The client runtime.</param>
        public void ApplyClientBehavior(ContractDescription contractDescription, ServiceEndpoint endpoint, ClientRuntime clientRuntime)
        {
            // No additional client behaviors.
        }

        /// <summary>
        /// Configures any binding elements to support the contract behavior.
        /// </summary>
        /// <param name="contractDescription">The contract description to modify.</param>
        /// <param name="endpoint">The endpoint to modify.</param>
        /// <param name="bindingParameters">The objects that binding elements require to support the behavior.</param>
        public void AddBindingParameters(ContractDescription contractDescription, ServiceEndpoint endpoint, BindingParameterCollection bindingParameters)
        {
            // No additional binding parameters.
        }

        #endregion

        #region Implementation of IContractBehaviorAttribute

        /// <summary>
        /// Gets the type of the contract to which the contract behavior is applicable.
        /// </summary>
        /// <returns>
        /// The contract to which the contract behavior is applicable.
        /// </returns>
        /// <remarks>
        /// Always return null because in this case, we want to indicate that the behavior should apply to any service decorated with the policy injection attribute.
        /// </remarks>
        public Type TargetContract
        {
            get { return null; }
        }

        #endregion

    }
}
