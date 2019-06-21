package it.cnr.si.opencmis.criteria;

import it.cnr.si.opencmis.criteria.restrictions.Restrictions;

import java.util.HashMap;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.api.SessionFactory;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueryTest {
	private Session cmisSession;
	
	@Before
    public void setup() {
		SessionFactory f = SessionFactoryImpl.newInstance();
		Map<String, String> parameter = new HashMap<String, String>();

		// user credentials
		parameter.put(SessionParameter.USER, "admin");
		parameter.put(SessionParameter.PASSWORD, "admin");

		// connection settings
		parameter.put(SessionParameter.ATOMPUB_URL, "http://cmis.alfresco.com/api/-default-/public/cmis/versions/1.1/atom");
		parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
		parameter.put(SessionParameter.REPOSITORY_ID, f.getRepositories(parameter).get(0).getId());

		// session locale
		parameter.put(SessionParameter.LOCALE_ISO3166_COUNTRY, "IT");
		parameter.put(SessionParameter.LOCALE_ISO639_LANGUAGE, "it");
		parameter.put(SessionParameter.LOCALE_VARIANT, "");

		// create session
		cmisSession = f.createSession(parameter);		
    }	
	
	@Test
	public void queryApostrophe(){
		Criteria criteria = CriteriaFactory.createCriteria(BaseTypeId.CMIS_FOLDER.value());
		criteria.add(Restrictions.eq(PropertyIds.NAME, "prova'prova"));
		ItemIterable<QueryResult> queryResults = criteria.executeQuery(cmisSession, false, cmisSession.getDefaultContext());
		for (QueryResult queryResult : queryResults) {
			Assert.assertEquals("prova'prova", queryResult.getPropertyValueById(PropertyIds.NAME));
		}
	}
}
