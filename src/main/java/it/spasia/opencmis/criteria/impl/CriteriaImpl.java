package it.spasia.opencmis.criteria.impl;

/*
 * Copyright 2010 Marco Spasiano (marco.spasiano@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * $Id: CriteriaImpl.java 1 2010-12-09 11:44:57Z marco.spasiano $
 */

import it.spasia.opencmis.criteria.CMISContext;
import it.spasia.opencmis.criteria.CMISParameterValue;
import it.spasia.opencmis.criteria.Criteria;
import it.spasia.opencmis.criteria.Criterion;
import it.spasia.opencmis.criteria.JoinType;
import it.spasia.opencmis.criteria.Order;
import it.spasia.opencmis.criteria.ResultTransformer;
import it.spasia.opencmis.criteria.Utils;
import it.spasia.opencmis.criteria.transformers.Transformers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ExtensionsData;


/**
 * Default implementation of it.spasia.opencmis.criteria.Criteria and
 * it.spasia.opencmis.criteria.CMISContext including subcriteria logic.
 *
 * @author <a href="mailto:marco.spasiano@gmail.com">Marco Spasiano</a>
 * @version $Revision: 1 $
 */
public class CriteriaImpl
    implements Criteria, CMISContext
{
    private static final long serialVersionUID = 1L;

    /**
     * Constant for default root type alias.
     */
    private static final String ROOT_TYPE_ALIAS = "this";

    /**
     * Constant for default unique name prefix.
     */
    private static final String ALIAS_PREFIX = "_alias";

    /**
     * Root type name.
     */
    private final String rootTypeName;

    /**
     * Root type alias.
     */
    private final String rootTypeAlias;

    /**
     * Holds all criteria's and subcriterias' restrictions elements.
     */
    private final List<Criterion> criterionElements =
        new ArrayList<Criterion>();

    /**
     * Holds all criteria's and subcriterias' restrictions elements.
     */
    private final Map<Criteria, Criterion> criterionJoinElements = new HashMap<Criteria, Criterion>();
    
    /**
     * Holds all criteria's and subcriterias' parameter names and their values.
     */
    private final Map<String, CMISParameterValue<?>> parameters =
        new HashMap<String, CMISParameterValue<?>>();

    /**
     * Holds all criteria's and subcriterias' ordering elements.
     */
    private final List<Order> orderingElements = new ArrayList<Order>();

    /**
     * Holds all criteria's and subcriterias' fetch elements.
     */
    private final List<String> columns = new ArrayList<String>();

    /**
     * Result transformer (SELECT clause generator) of this criteria.
     */
    private ResultTransformer resultTransformer = Transformers.rootType();

    /**
     * Subcriterias elements of this criteria.
     */
    private final List<Criteria> subcriteriaElements =
        new ArrayList<Criteria>();

    /**
     * Generator of unique parameter names for this instance of criteria.
     */
    private final UniqueParameterNameGenerator parameterNameGenerator =
        new UniqueParameterNameGenerator();

    /**
     * Creates criteria with specified root entity name and default alias.
     *
     * @param anTypeName
     *            root criteria type name
     */
    public CriteriaImpl( String anTypeName )
    {
        this( anTypeName, ROOT_TYPE_ALIAS );
    }

    /**
     * Creates criteria with specified root type name and alias.
     *
     * @param anTypeId
     *            root criteria entity name
     * @param anAlias
     *            root criteria entity alias
     */
    public CriteriaImpl( String anTypeId, String anAlias )
    {
        this.rootTypeName = anTypeId;
        this.rootTypeAlias = anAlias;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criteria#add(it.spasia.opencmis.criteria.Criterion)
     */
    public Criteria add( Criterion criterion )
    {
        this.criterionElements.add( criterion );

        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criteria#addOrder(it.spasia.opencmis.criteria.Order)
     */
    public Criteria addOrder( Order order )
    {
        this.orderingElements.add( order );

        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criteria#createCriteria(java.lang.String)
     */
    public Criteria createCriteria( String type)
    {
        return createCriteria( type, this.generateAlias());
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criteria#createCriteria(java.lang.String,
     *      it.spasia.opencmis.criteria.JoinType)
     */
    public Criteria createCriteria( String type, JoinType joinType)
    {
        return createCriteria( type, this.generateAlias(), joinType);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criteria#createCriteria(java.lang.String,
     *      java.lang.String)
     */
    public Criteria createCriteria( String type, String alias)
    {
        return createCriteria( type, alias, JoinType.DEFAULT);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criteria#createCriteria(java.lang.String,
     *      java.lang.String, it.spasia.opencmis.criteria.JoinType)
     */
    public Criteria createCriteria( String type, String alias,
                                    JoinType joinType)
    {
        return new Subcriteria( this, type , alias, joinType);
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criteria#prepareQuery(javax.persistence.EntityManager)
     */
    public ItemIterable<QueryResult> executeQuery( Session cmisSession , boolean searchAllVersions, OperationContext context)
    {
        return executeQuery( this, cmisSession, searchAllVersions, context, null);
    }

    public ItemIterable<QueryResult> executeQuery( Session cmisSession , boolean searchAllVersions, OperationContext context, ExtensionsData extension)
    {
        return executeQuery( this, cmisSession, searchAllVersions, context, extension);
    }

    /**
     * Uses CMISBuilder instance to build CMIS query.
     *
     * @param cmisContext cmis context used to build query
     * @param cmisSession
     * @param searchAllVersions
     * @param context
     * @return
     */
    private ItemIterable<QueryResult> executeQuery( CMISContext cmisContext,
    		 					Session cmisSession , 
    		 					boolean searchAllVersions, 
    		 					OperationContext context,
    		 					ExtensionsData extension)
    {
        CMISBuilder builder = new CMISBuilder( cmisContext, cmisSession );
        ItemIterable<QueryResult> result = builder.executeQuery(searchAllVersions, context, extension);

        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.CMISContext#getCriterionElements()
     */
    public List<Criterion> getCriterionElements()
    {
        return Collections.unmodifiableList( this.criterionElements );
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.CMISContext#getCriterionElements()
     */
    public Map<Criteria, Criterion> getCriterionJoinElements()
    {
        return Collections.unmodifiableMap( this.criterionJoinElements);
    }
    
    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.CMISContext#getFetchElements()
     */
    public List<String> getColumns()
    {
        return Collections.unmodifiableList( this.columns );
    }

	public void addColumn(String propertyName) {
		columns.add(propertyName);
	}

	public void removeColumn(String propertyName) {
		columns.remove(propertyName);
	}

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.CMISContext#getSubcriteriaElements()
     */
    public List<Criteria> getSubcriteriaElements()
    {
        return Collections.unmodifiableList( this.subcriteriaElements );
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.CMISContext#getOrderingElements()
     */
    public List<Order> getOrderingElements()
    {
        return Collections.unmodifiableList( this.orderingElements );
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.CMISContext#generateParameterName(java.lang.String,
     *      it.spasia.opencmis.criteria.CMISParameterValue)
     */
    public String generateParameterName( String propertyName,
                                         CMISParameterValue<?> parameterValue )
    {
        String parameterName =
            this.parameterNameGenerator.generateNextParameterName( propertyName );

        this.parameters.put( parameterName, parameterValue );

        return parameterName;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.CMISContext#getParameters()
     */
    public Map<String, CMISParameterValue<?>> getParameters()
    {
        return Collections.unmodifiableMap( this.parameters );
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criteria#getRootTypeAlias()
     */
    public String getRootTypeAlias()
    {
        return this.rootTypeAlias;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criteria#getRootTypeName()
     */
    public String getRootTypeName()
    {
        return this.rootTypeName;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.CMISContext#generateAlias()
     */
    public synchronized String generateAlias()
    {
        return this.parameterNameGenerator.generateNextParameterName( ALIAS_PREFIX );
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.Criteria#getEntityAlias()
     */
    public String getTypeAlias()
    {
        return this.rootTypeAlias;
    }

    /*
     * (non-Javadoc)
     *
     * @see it.spasia.opencmis.criteria.CMISContext#getResultTransformer()
     */
    public ResultTransformer getResultTransformer()
    {
        return this.resultTransformer;
    }
    
    public String prefix( String path )
    {
        return prefixAssocationPathIfNeeded( path, this.getTypeAlias() );
    }
    
    protected String prefixAssocationPathIfNeeded( String path, String prefix )
    {
        if ( path != null )
        {
            if ( path.startsWith( getRootTypeAlias() + "." ) )
            {
                return path;
            }
            for ( Criteria alias : this.subcriteriaElements )
            {
                if ( path.startsWith( alias.getTypeAlias() + "." ) )
                {
                    alias.getRootTypeAlias();
                    return path;
                }
            }
            
            return prefix + "." + path;
        }
        
        return path;
    }

    public Criterion addJoinCriterion(Criterion joinCriterion) {
		throw new RuntimeException("Cannot add join to Criteria");
	}

    /**
     * Implementation of subcriteria class. Many methods delegate to enclosing
     * CriteriaImpl class, but some are handled locally specifically to
     * subcriteria.
     *
     * This class is instance class to provide access to enclosing <b>root<b>
     * criteria. However, parent criteria can be either CriteriaImpl or
     * Subcriteria instance.
     */
    public final class Subcriteria
        implements Criteria, CMISContext
    {
        private static final long serialVersionUID = 1L;

        /**
         * Subcriteria alias.
         */
        private final String alias;

        /**
         * Assocation path this subcriteria is reffering to.
         */
        private final String type;

        /**
         * Parent criteria. It can be either CriteriaImpl or Subcriteria
         * instance.
         */
        private final Criteria parent;

        /**
         * Specifies join type to use joining from parent criteria.
         */
        private final JoinType joinType;
        
        private final List<String> columns = new ArrayList<String>();
        /**
         * Constructor providing all possible parameters.
         *
         * @param aParent
         *            parent criteria (CriteriaImpl or Subcriteria)
         * @param aType
         * @param anAlias
         *            alias of this subcriteria
         * @param aJoinType
         *            join type for joining this subcriteria with parent
         *            criteria
         */
        private Subcriteria( Criteria aParent, String aType,
                             String anAlias, JoinType aJoinType)
        {
            this.alias = anAlias;

            this.type = aType;
            this.parent = aParent;
            this.joinType = aJoinType;
            CriteriaImpl.this.subcriteriaElements.add( this );
        }

        /**
         * Constructor building subcriteria for specified path. Alias will be
         * autogenerated.
         *
         * @param aParent
         *            parent criteria (CriteriaImpl or Subcriteria)
         * @param aType
         */
        private Subcriteria( Criteria aParent, String aType)
        {
            this( aParent, aType, CriteriaImpl.this.generateAlias(),
                  JoinType.DEFAULT);
        }
        
        public Criterion addJoinCriterion( Criterion joinCriterion ){
        	return CriteriaImpl.this.criterionJoinElements.put(this, joinCriterion);
    	}
        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.Criteria#add(it.spasia.opencmis.criteria.Criterion)
         */
        public Criteria add( Criterion criterion )
        {
            CriteriaImpl.this.add( criterion );

            return this;
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.Criteria#addOrder(it.spasia.opencmis.criteria.Order)
         */
        public Criteria addOrder( Order order )
        {
            CriteriaImpl.this.addOrder( order );

            return this;
        }


        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.Criteria#createCriteria(java.lang.String)
         */
        public Criteria createCriteria( String type)
        {
            return new Subcriteria(
                                    Subcriteria.this,
                                    type );
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.Criteria#createCriteria(java.lang.String,
         *      it.spasia.opencmis.criteria.JoinType)
         */
        public Criteria createCriteria( String type, JoinType joinType)
        {
            return new Subcriteria( Subcriteria.this, type, null, joinType);
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.Criteria#createCriteria(java.lang.String,
         *      java.lang.String)
         */
        public Criteria createCriteria( String type, String alias)
        {
            return new Subcriteria(
                                    Subcriteria.this,
                                    type,
                                    alias, JoinType.DEFAULT);
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.Criteria#createCriteria(java.lang.String,
         *      java.lang.String, it.spasia.opencmis.criteria.JoinType)
         */
        public Criteria createCriteria( String type, String alias,
                                        JoinType joinType)
        {
            return new Subcriteria( Subcriteria.this, prefix( type ), alias, joinType);
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.Criteria#prepareQuery(javax.persistence.EntityManager)
         */
        public ItemIterable<QueryResult> executeQuery( Session cmisSession , boolean searchAllVersions, OperationContext context)
        {
            return CriteriaImpl.this.executeQuery( this, cmisSession, searchAllVersions, context, null);
        }

        public ItemIterable<QueryResult> executeQuery( Session cmisSession , boolean searchAllVersions, OperationContext context, ExtensionsData extension)
        {
            return CriteriaImpl.this.executeQuery( this, cmisSession, searchAllVersions, context, extension);
        }
        
        /**
         * Returns join type used for joining this subcriteria.
         *
         * @return join type used for joining this subcriteria
         */
        JoinType getJoinType()
        {
            return this.joinType;
        }

        /**
         * Returns assocation path of this subcriteria.
         *
         * @return assocation path of this subcriteria
         */
        String getType()
        {
            return this.type;
        }

        /**
         * Returns parent criteria (CriteriaImpl or Subcriteria).
         *
         * @return parent criteria
         */
        Criteria getParent()
        {
            return this.parent;
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.Criteria#getTypeAlias()
         */
        public String getTypeAlias()
        {
            return this.alias;
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.Criteria#getRootTypeAlias()
         */
        public String getRootTypeAlias()
        {
            return CriteriaImpl.this.getRootTypeAlias();
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.Criteria#getRootTypeName()
         */
        public String getRootTypeName()
        {
            return CriteriaImpl.this.getRootTypeName();
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.CMISContext#generateAlias()
         */
        public String generateAlias()
        {
            return CriteriaImpl.this.generateAlias();
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.CMISContext#generateParameterName(java.lang.String,
         *      it.spasia.opencmis.criteria.CMISParameterValue)
         */
        public String generateParameterName( String propertyName,
                                             CMISParameterValue<?> parameterValue )
        {
            return CriteriaImpl.this.generateParameterName( propertyName,
                                                            parameterValue );
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.CMISContext#getParameters()
         */
        public Map<String, CMISParameterValue<?>> getParameters()
        {
            return CriteriaImpl.this.getParameters();
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.CMISContext#getSubcriteriaElements()
         */
        public List<Criteria> getSubcriteriaElements()
        {
            return CriteriaImpl.this.getSubcriteriaElements();
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.CMISContext#getOrderingElements()
         */
        public List<Order> getOrderingElements()
        {
            return Collections.unmodifiableList( CriteriaImpl.this.orderingElements );
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.CMISContext#getCriterionElements()
         */
        public List<Criterion> getCriterionElements()
        {
            return CriteriaImpl.this.getCriterionElements();
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.CMISContext#getCriterionElements()
         */
        public Map<Criteria, Criterion> getCriterionJoinElements()
        {
            return CriteriaImpl.this.getCriterionJoinElements();
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.CMISContext#getColumns()
         */
        public List<String> getColumns()
        {
            return columns;
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.CMISContext#addColumn()
         */
        public void addColumn(String propertyName){
        	columns.add(propertyName);
        }

        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.CMISContext#removeColumn()
         */
        public void removeColumn(String propertyName){
        	columns.remove(propertyName);
        }
        
        /*
         * (non-Javadoc)
         *
         * @see it.spasia.opencmis.criteria.CMISContext#getResultTransformer()
         */
        public ResultTransformer getResultTransformer()
        {
            return CriteriaImpl.this.getResultTransformer();
        }

        public String prefix( String path )
        {
            return CriteriaImpl.this.prefixAssocationPathIfNeeded( path, this.getTypeAlias() );
        }


    }

    /**
     * Utility class for generation unique names for query parameters and
     * aliases.
     */
    public static class UniqueParameterNameGenerator
    {
        /**
         * Constant for starting number for unique names generation.
         */
        private static final Integer INITIAL_VALUE = new Integer( 1 );

        /**
         * Constant for separating ALIAS_PREFIX and next unique number.
         */
        private static final String SEPARATOR = "_";

        /**
         * Map of names for which currently sequence numbers where generated.
         */
        private final Map<String, Integer> parametersIds =
            new HashMap<String, Integer>();

        /**
         * Generates new parameter name based on property name which is prefix
         * for generated name.
         *
         * @param propertyName
         *            prefix for generated parameter name
         * @return unique parameter name
         */
        public synchronized String generateNextParameterName(
                                                              String propertyName )
        {
            StringBuilder buffer = new StringBuilder();
            propertyName = Utils.unqualifyAssocationPath( propertyName );
            buffer.append( propertyName );
            buffer.append( SEPARATOR );

            if ( parametersIds.containsKey( propertyName ) )
            {
                Integer newValue =
                    new Integer(
                                 parametersIds.get( propertyName ).intValue() + 1 );
                parametersIds.put( propertyName, newValue );
                buffer.append( newValue.toString() );
            }
            else
            {
                parametersIds.put( propertyName, INITIAL_VALUE );
                buffer.append( INITIAL_VALUE.toString() );
            }

            buffer.append( SEPARATOR );

            return buffer.toString();
        }
    }
}
