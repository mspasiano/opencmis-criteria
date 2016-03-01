### Goal of opencmis-criteria library is to provide an API based on Hibernate Criteria API design for CMIS query. ###

---

Use OpenCMIS Criteria with Maven
```
<repositories>
  <repository>
    <id>opencmis-criteria.snapshots</id>
    <name>OpenCMIS Criteria snapshots repository</name>
    <url>http://opencmis-criteria.googlecode.com/svn/maven2repo/snapshots</url>
  </repository>
</repositories>

<dependency>
    <groupId>it.spasia</groupId>
    <artifactId>opencmis-criteria</artifactId>
    <version>0.1-SNAPSHOT</version>
</dependency>
```

### Example Code ###

---

```
SessionFactory f = SessionFactoryImpl.newInstance();
Map<String, String> parameter = new HashMap<String, String>();

// user credentials
parameter.put(SessionParameter.USER, "Otto");
parameter.put(SessionParameter.PASSWORD, "****");

// connection settings
parameter.put(SessionParameter.ATOMPUB_URL, "http://<host>:<port>/cmis/atom");
parameter.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
parameter.put(SessionParameter.REPOSITORY_ID, "myRepository");

// session locale
parameter.put(SessionParameter.LOCALE_ISO3166_COUNTRY, "");
parameter.put(SessionParameter.LOCALE_ISO639_LANGUAGE, "de");
parameter.put(SessionParameter.LOCALE_VARIANT, "");

// create session
Session s = f.createSession(parameter);
// create Criteria
Criteria criteria = CriteriaFactory.createCriteria(BaseTypeId.CMIS_DOCUMENT.value(), "doc");
Criteria criteriaTit = criteria.createCriteria("cm:titled","tit");
criteriaTit.addJoinCriterion(Restrictions.eqProperty(
				criteria.prefix(PropertyIds.OBJECT_ID), 
				criteriaTit.prefix(PropertyIds.OBJECT_ID)));
Criteria criteriaAut = criteria.createCriteria("cm:author","aut");
criteriaAut.addJoinCriterion(Restrictions.eqProperty(
				criteria.prefix(PropertyIds.OBJECT_ID), 
				criteriaAut.prefix(PropertyIds.OBJECT_ID)));
//Execute Criteria Query
ItemIterable<QueryResult> queryResult = criteria.executeQuery(s, true, s.createOperationContext());

```