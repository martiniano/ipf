/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openehealth.ipf.modules.cda;

import java.util.Map;

import org.openehealth.ipf.commons.core.modules.api.ValidationException;
import org.openehealth.ipf.commons.core.modules.api.Validator;
import org.openhealthtools.mdht.uml.cda.CDAPackage;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.ccd.CCDPackage;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil.ValidationHandler;

/**
 * Validates a ClinicalDocuments instance for conformity against CDA and/or CCD
 * schema.
 * 
 * @author Stefan Ivanov
 * 
 */
public class CDAR2Validator implements Validator<ClinicalDocument, Map<Object, Object>> {
    
    static {
        @SuppressWarnings("unused")
        CDAPackage cdaPackageInstance = CDAPackage.eINSTANCE;
        @SuppressWarnings("unused")
        CCDPackage ccdPackageInstance = CCDPackage.eINSTANCE;
    }

    @Override
    public void validate(ClinicalDocument doc, Map<Object, Object> context) {
        boolean isValid = CDAUtil.validate(doc, retrieveValidationHandler(context));
        if (! isValid) {
            throw new ValidationException("Clinical Document not valid!");
        }
    }
    
    /**
     * Retrieves validation handler from context. If none found returns a
     * default validation handler.
     * 
     * @param context
     * @return
     */
    private ValidationHandler retrieveValidationHandler(Map<Object, Object> context) {
        if (context != null) {
            ValidationHandler handler = (ValidationHandler) context.get(ValidationHandler.class);
            if (handler != null) {
                return handler;
            }
        }
        return new DefaultValidationHandler();
    }

}
