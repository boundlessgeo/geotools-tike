/*
 *    GeoTools - The Open Source Java GIS Toolkit
 *    http://geotools.org
 * 
 *    (C) 2002-2008, Open Source Geospatial Foundation (OSGeo)
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 */
package org.geotools.filter.visitor;

import org.opengis.filter.And;
import org.opengis.filter.ExcludeFilter;
import org.opengis.filter.FilterVisitor;
import org.opengis.filter.Id;
import org.opengis.filter.IncludeFilter;
import org.opengis.filter.Not;
import org.opengis.filter.Or;
import org.opengis.filter.PropertyIsBetween;
import org.opengis.filter.PropertyIsEqualTo;
import org.opengis.filter.PropertyIsGreaterThan;
import org.opengis.filter.PropertyIsGreaterThanOrEqualTo;
import org.opengis.filter.PropertyIsLessThan;
import org.opengis.filter.PropertyIsLessThanOrEqualTo;
import org.opengis.filter.PropertyIsLike;
import org.opengis.filter.PropertyIsNotEqualTo;
import org.opengis.filter.PropertyIsNull;
import org.opengis.filter.capability.ArithmeticOperators;
import org.opengis.filter.capability.ComparisonOperators;
import org.opengis.filter.capability.FilterCapabilities;
import org.opengis.filter.capability.FunctionName;
import org.opengis.filter.capability.Functions;
import org.opengis.filter.capability.ScalarCapabilities;
import org.opengis.filter.capability.SpatialCapabilities;
import org.opengis.filter.capability.SpatialOperators;
import org.opengis.filter.expression.Add;
import org.opengis.filter.expression.Divide;
import org.opengis.filter.expression.ExpressionVisitor;
import org.opengis.filter.expression.Function;
import org.opengis.filter.expression.Literal;
import org.opengis.filter.expression.Multiply;
import org.opengis.filter.expression.NilExpression;
import org.opengis.filter.expression.PropertyName;
import org.opengis.filter.expression.Subtract;
import org.opengis.filter.spatial.BBOX;
import org.opengis.filter.spatial.Beyond;
import org.opengis.filter.spatial.Contains;
import org.opengis.filter.spatial.Crosses;
import org.opengis.filter.spatial.DWithin;
import org.opengis.filter.spatial.Disjoint;
import org.opengis.filter.spatial.Equals;
import org.opengis.filter.spatial.Intersects;
import org.opengis.filter.spatial.Overlaps;
import org.opengis.filter.spatial.Touches;
import org.opengis.filter.spatial.Within;

/**
 * This visitor will return Boolean.TRUE if the provided filter
 * is supported by the the FilterCapabilities. 
 * <p>
 * This method will look up the right information in the provided
 * FilterCapabilities instance for you depending on the type of filter
 * provided.
 * <p>
 * Example:<pre><code>
 * boolean yes = filter.accepts( IsSupportedFilterVisitor( capabilities ), null );
 * </code></pre> 
 * 
 * Please consider IsSupportedFilterVisitor if you need to be sure of the
 * entire Filter.
 * 
 * @author Jody Garnett (Refractions Research)
 *
 * @source $URL$
 */
public class IsSupportedFilterVisitor implements FilterVisitor, ExpressionVisitor {
    
    private FilterCapabilities capabilities;

    public IsSupportedFilterVisitor( FilterCapabilities capabilities ){
        this.capabilities = capabilities;
    }
    
    /** INCLUDE and EXCLUDE are never supported */
    public Object visit( ExcludeFilter filter, Object extraData ) {
        return false;
    }
    /** INCLUDE and EXCLUDE are never supported */
    public Object visit( IncludeFilter filter, Object extraData ) {
        return false;
    }
     
    public Object visit( And filter, Object extraData ) {
        return capabilities.getScalarCapabilities() != null &&
               capabilities.getScalarCapabilities().hasLogicalOperators();
    }

    public Object visit( Id filter, Object extraData ) {
        return capabilities.getIdCapabilities() != null &&
               ( capabilities.getIdCapabilities().hasFID() ||
                 capabilities.getIdCapabilities().hasEID() );
    }

    public Object visit( Not filter, Object extraData ) {
        return capabilities.getScalarCapabilities() != null &&
        capabilities.getScalarCapabilities().hasLogicalOperators();
    }

    public Object visit( Or filter, Object extraData ) {
        return capabilities.getScalarCapabilities() != null &&
        capabilities.getScalarCapabilities().hasLogicalOperators();
    }

    public Object visit( PropertyIsBetween filter, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;
        
        ComparisonOperators operators = scalar.getComparisonOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( PropertyIsBetween.NAME ) != null;
    }

    public Object visit( PropertyIsEqualTo filter, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;
        
        ComparisonOperators operators = scalar.getComparisonOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( PropertyIsEqualTo.NAME ) != null;
    }

    public Object visit( PropertyIsNotEqualTo filter, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;
        
        ComparisonOperators operators = scalar.getComparisonOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( PropertyIsNotEqualTo.NAME ) != null;
    }

    public Object visit( PropertyIsGreaterThan filter, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;
        
        ComparisonOperators operators = scalar.getComparisonOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( PropertyIsGreaterThan.NAME ) != null;
    }

    public Object visit( PropertyIsGreaterThanOrEqualTo filter, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;
        
        ComparisonOperators operators = scalar.getComparisonOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( PropertyIsGreaterThanOrEqualTo.NAME ) != null;
    }

    public Object visit( PropertyIsLessThan filter, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;
        
        ComparisonOperators operators = scalar.getComparisonOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( PropertyIsLessThan.NAME ) != null;
    }

    public Object visit( PropertyIsLessThanOrEqualTo filter, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;
        
        ComparisonOperators operators = scalar.getComparisonOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( PropertyIsLessThanOrEqualTo.NAME ) != null;
    }

    public Object visit( PropertyIsLike filter, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;
        
        ComparisonOperators operators = scalar.getComparisonOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( PropertyIsLike.NAME ) != null;
    }

    public Object visit( PropertyIsNull filter, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;
        
        ComparisonOperators operators = scalar.getComparisonOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( PropertyIsNull.NAME ) != null;
    }

    public Object visit( BBOX filter, Object extraData ) {
        SpatialCapabilities spatial = capabilities.getSpatialCapabilities();
        if( spatial == null ) return false;
        
        SpatialOperators operators = spatial.getSpatialOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( BBOX.NAME ) != null;
    }

    public Object visit( Beyond filter, Object extraData ) {
        SpatialCapabilities spatial = capabilities.getSpatialCapabilities();
        if( spatial == null ) return false;
        
        SpatialOperators operators = spatial.getSpatialOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( Beyond.NAME ) != null;
    }

    public Object visit( Contains filter, Object extraData ) {
        SpatialCapabilities spatial = capabilities.getSpatialCapabilities();
        if( spatial == null ) return false;
        
        SpatialOperators operators = spatial.getSpatialOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( Contains.NAME ) != null;
    }

    public Object visit( Crosses filter, Object extraData ) {
        SpatialCapabilities spatial = capabilities.getSpatialCapabilities();
        if( spatial == null ) return false;
        
        SpatialOperators operators = spatial.getSpatialOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( Crosses.NAME ) != null;
    }

    public Object visit( Disjoint filter, Object extraData ) {
        SpatialCapabilities spatial = capabilities.getSpatialCapabilities();
        if( spatial == null ) return false;
        
        SpatialOperators operators = spatial.getSpatialOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( Disjoint.NAME ) != null;
    }

    public Object visit( DWithin filter, Object extraData ) {
        SpatialCapabilities spatial = capabilities.getSpatialCapabilities();
        if( spatial == null ) return false;
        
        SpatialOperators operators = spatial.getSpatialOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( DWithin.NAME ) != null;
    }

    public Object visit( Equals filter, Object extraData ) {
        SpatialCapabilities spatial = capabilities.getSpatialCapabilities();
        if( spatial == null ) return false;
        
        SpatialOperators operators = spatial.getSpatialOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( Equals.NAME ) != null;
    }

    public Object visit( Intersects filter, Object extraData ) {
        SpatialCapabilities spatial = capabilities.getSpatialCapabilities();
        if( spatial == null ) return false;
        
        SpatialOperators operators = spatial.getSpatialOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( Intersects.NAME ) != null;
    }

    public Object visit( Overlaps filter, Object extraData ) {
        SpatialCapabilities spatial = capabilities.getSpatialCapabilities();
        if( spatial == null ) return false;
        
        SpatialOperators operators = spatial.getSpatialOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( Overlaps.NAME ) != null;
    }

    public Object visit( Touches filter, Object extraData ) {
        SpatialCapabilities spatial = capabilities.getSpatialCapabilities();
        if( spatial == null ) return false;
        
        SpatialOperators operators = spatial.getSpatialOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( Touches.NAME ) != null;
    }

    public Object visit( Within filter, Object extraData ) {
        SpatialCapabilities spatial = capabilities.getSpatialCapabilities();
        if( spatial == null ) return false;
        
        SpatialOperators operators = spatial.getSpatialOperators();
        if( operators == null ) return false;
        
        return operators.getOperator( Within.NAME ) != null;
    }

    public Object visitNullFilter( Object extraData ) {
        return false;
    }

    //
    // Expressions
    //
    /** NilExpression is a placeholder and is never supported */
    public Object visit( NilExpression expression, Object extraData ) {
        return false;
    }
    
    public Object visit( Add expression, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;        
        
        ArithmeticOperators operators = scalar.getArithmeticOperators();
        if( operators == null ) return false;
        
        return operators.hasSimpleArithmetic();
    }

    public Object visit( Divide expression, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;        
        
        ArithmeticOperators operators = scalar.getArithmeticOperators();
        if( operators == null ) return false;
        
        return operators.hasSimpleArithmetic();
    }

    public Object visit( Function function, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;        
        
        ArithmeticOperators operators = scalar.getArithmeticOperators();
        if( operators == null ) return false;
        
        Functions functions = operators.getFunctions();
        if( functions == null ) return false;
        
        // Note that only function name is checked here
        FunctionName found = functions.getFunctionName( function.getName() );
        // And that's enough to assess if the function is supported
        return found != null;
    }

    public Object visit( Literal expression, Object extraData ) {
        return true;
    }

    public Object visit( Multiply expression, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;        
        
        ArithmeticOperators operators = scalar.getArithmeticOperators();
        if( operators == null ) return false;
        
        return operators.hasSimpleArithmetic();
    }

    /**
     * You can override this to perform a sanity check against a provided
     * FeatureType.
     */
    public Object visit( PropertyName expression, Object extraData ) {
        return true;
    }

    public Object visit( Subtract expression, Object extraData ) {
        ScalarCapabilities scalar = capabilities.getScalarCapabilities();
        if( scalar == null ) return false;        
        
        ArithmeticOperators operators = scalar.getArithmeticOperators();
        if( operators == null ) return false;
        
        return operators.hasSimpleArithmetic();
    }        
}
