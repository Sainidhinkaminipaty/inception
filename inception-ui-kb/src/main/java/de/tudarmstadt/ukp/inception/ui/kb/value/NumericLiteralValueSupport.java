/*
 * Copyright 2018
 * Ubiquitous Knowledge Processing (UKP) Lab
 * Technische Universität Darmstadt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tudarmstadt.ukp.inception.ui.kb.value;

import static java.util.Arrays.asList;

import java.util.List;
import java.util.Optional;

import org.apache.wicket.model.IModel;
import org.cyberborean.rdfbeans.datatype.DefaultDatatypeMapper;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.vocabulary.XMLSchema;
import org.springframework.stereotype.Component;

import de.tudarmstadt.ukp.inception.kb.graph.KBObject;
import de.tudarmstadt.ukp.inception.kb.graph.KBProperty;
import de.tudarmstadt.ukp.inception.kb.graph.KBStatement;
import de.tudarmstadt.ukp.inception.kb.model.KnowledgeBase;
import de.tudarmstadt.ukp.inception.ui.kb.value.editor.NumericLiteralValueEditor;
import de.tudarmstadt.ukp.inception.ui.kb.value.editor.NumericLiteralValuePresenter;
import de.tudarmstadt.ukp.inception.ui.kb.value.editor.ValueEditor;
import de.tudarmstadt.ukp.inception.ui.kb.value.editor.ValuePresenter;

@Component
public class NumericLiteralValueSupport
    implements ValueTypeSupport
{
    private String valueTypeSupportId;
    
    @Override
    public String getId()
    {
        return valueTypeSupportId;
    }
    
    @Override
    public void setBeanName(String aBeanName)
    {
        valueTypeSupportId = aBeanName;
    }
    
    @Override
    public List<ValueType> getSupportedValueTypes()
    {
        return asList(new ValueType(XMLSchema.DOUBLE.stringValue(), "Numeric", valueTypeSupportId));
    }
    
    @Override
    public boolean accepts(KBStatement aStatement, KBProperty aProperty)
    {
        if (aStatement.getValue() == null) {
            return false;
        }
        IRI iri = DefaultDatatypeMapper.getDatatypeURI((aStatement.getValue()).getClass());
        Boolean acceptsType = iri.equals(XMLSchema.INTEGER)
            || iri.equals(XMLSchema.INT)
            || iri.equals(XMLSchema.NON_NEGATIVE_INTEGER)
            || iri.equals(XMLSchema.NON_POSITIVE_INTEGER)
            || iri.equals(XMLSchema.LONG)
            || iri.equals(XMLSchema.FLOAT)
            || iri.equals(XMLSchema.NEGATIVE_INTEGER)
            || iri.equals(XMLSchema.POSITIVE_INTEGER)
            || iri.equals(XMLSchema.UNSIGNED_INT)
            || iri.equals(XMLSchema.UNSIGNED_LONG)
            || iri.equals(XMLSchema.UNSIGNED_SHORT)
            || iri.equals(XMLSchema.SHORT)
            || iri.equals(XMLSchema.DOUBLE);

        return iri != null && acceptsType;
    }
    
    @Override
    public boolean accepts(String range, Optional<KBObject> rangeKbObject)
    {
        if (range != null && (range.equals(XMLSchema.INTEGER.stringValue())
                || range.equals(XMLSchema.INT.stringValue())
                || range.equals(XMLSchema.NON_NEGATIVE_INTEGER.stringValue()) 
                || range.equals(XMLSchema.NON_POSITIVE_INTEGER.stringValue())
                || range.equals(XMLSchema.LONG.stringValue())
                || range.equals(XMLSchema.FLOAT.stringValue())
                || range.equals(XMLSchema.NEGATIVE_INTEGER.stringValue())
                || range.equals(XMLSchema.POSITIVE_INTEGER.stringValue())
                || range.equals(XMLSchema.UNSIGNED_INT.stringValue())
                || range.equals(XMLSchema.UNSIGNED_LONG.stringValue())
                || range.equals(XMLSchema.UNSIGNED_SHORT.stringValue())
                || range.equals(XMLSchema.SHORT.stringValue())
                || range.equals(XMLSchema.DOUBLE.stringValue()))) {
            return true;
        }
        
        return false;
    }

    

    @Override
    public ValueEditor createEditor(String aId, IModel<KBStatement> aStatement,
            IModel<KBProperty> aProperty, IModel<KnowledgeBase> kbModel)
    {
        return new NumericLiteralValueEditor(aId, aStatement);
    }

    @Override
    public ValuePresenter createPresenter(String aId, IModel<KBStatement> aStatement,
            IModel<KBProperty> aProperty)
    {
        return new NumericLiteralValuePresenter(aId, aStatement);
    }
        
}
