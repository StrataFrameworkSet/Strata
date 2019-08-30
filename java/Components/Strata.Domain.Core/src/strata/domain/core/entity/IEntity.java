//////////////////////////////////////////////////////////////////////////////
// IEntity.java
//////////////////////////////////////////////////////////////////////////////

package strata.domain.core.entity;

import java.time.Instant;

/*****************************************************************************
 * Common interface for all entity types.
 *
 * @param <K> primary id type
 */
public
interface IEntity<K>
{
    /*************************************************************************
     * Sets the primaryId property of the {@code IEntity<K>} instance.
     * @param  primaryId primary identifier
     * @return this {@code IEntity<K>} for method chaining
     */
    IEntity<K>
    setPrimaryId(K primaryId);

    /*************************************************************************
     * Sets the version property of the {@code IEntity<K>} instance.
     * @param  version version of entity (for optimistic locking)
     * @return this {@code IEntity<K>} for method chaining
     */
    IEntity<K>
    setVersion(Integer version);

    /*************************************************************************
     * Sets the created property of the {@code IEntity<K>} instance.
     * @param  created timestamp when entity was created
     * @return this {@code IEntity<K>} for method chaining
     */
    IEntity<K>
    setCreated(Instant created);

    /*************************************************************************
     * Sets the lastModified property of the {@code IEntity<K>} instance.
     * @param  lastModified timestamp when entity was last modified
     * @return this {@code IEntity<K>} for method chaining
     */
    IEntity<K>
    setLastModified(Instant lastModified);

    /*************************************************************************
     * Gets the primaryId property of the {@code IEntity<K>} instance.
     *
     * @return primaryId property of {@code IEntity<K>}
     */
    K
    getPrimaryId();

    /*************************************************************************
     * Gets the version property of the {@code IEntity<K>} instance.
     *
     * @return version property of {@code IEntity<K>}
     */
    Integer
    getVersion();

    /*************************************************************************
     * Gets the created property of the {@code IEntity<K>} instance.
     *
     * @return created property of {@code IEntity<K>}
     */
    Instant
    getCreated();

    /*************************************************************************
     * Gets the lastModified property of the {@code IEntity<K>} instance.
     *
     * @return lastModified property of {@code IEntity<K>}
     */
    Instant
    getLastModified();

}

//////////////////////////////////////////////////////////////////////////////