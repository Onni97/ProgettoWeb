/*
 * AA 2016-2017
 * Introduction to Web Programming
 * Common - DAO
 * UniTN
 */
package it.unitn.aa1920.webprogramming.sistemasanitario.DAO;

import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOException;
import it.unitn.aa1920.webprogramming.sistemasanitario.Exceptions.DAOFactoryException;

import java.util.List;

/**
 * The basic DAO interface that all DAOs must implement.
 *
 * @author Stefano Chirico &lt;stefano dot chirico at unitn dot it&gt;
 * @param <ENTITY_CLASS> the class of the entity to handle.
 * @param <PRIMARY_KEY> the class of the primary key of the entity the DAO
 * handle.
 * @since 2017.04.17
 */
public interface DAO<ENTITY_CLASS, PRIMARY_KEY> {


    /**
     * Returns the {@code ENTITY_CLASS} instance of the storage system record
     * with the primary key equals to the one passed as parameter.
     *
     * @param primaryKey the primary key used to obtain the entity instance.
     * @return the {@code ENTITY_CLASS} instance of the storage system record
     * with the primary key equals to the one passed as parameter or
     * {@code null} if no entities with that primary key is present into the
     * storage system.
     * @throws DAOException if an error occurred during the information
     * retrieving.
     *
     * @author Stefano Chirico
     * @since 1.0.170417
     */
    public ENTITY_CLASS getByPrimaryKey(PRIMARY_KEY primaryKey) throws DAOException;


    /**
     * If this DAO can interact with it, then returns the DAO of class passed as
     * parameter.
     *
     * @param <DAO_CLASS> the class name of the DAO that can interact with this
     * DAO.
     * @param daoClass the class of the DAO that can interact with this DAO.
     * @return the instance of the DAO or null if no DAO of the type passed as
     * parameter can interact with this DAO.
     * @throws DAOFactoryException if an error occurred.
     *
     * @author Stefano Chirico
     * @since 1.0.170417
     */
    public <DAO_CLASS extends DAO> DAO_CLASS getDAO(Class<DAO_CLASS> daoClass) throws DAOFactoryException;
}